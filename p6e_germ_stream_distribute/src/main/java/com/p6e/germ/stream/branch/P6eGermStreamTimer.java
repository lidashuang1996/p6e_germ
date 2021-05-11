package com.p6e.germ.stream.branch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时轮询任务执行器
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGermStreamTimer {

    /** 是否默认启动 */
    public static volatile boolean IS_DEFAULT_RUN = false;

    /** 是否运行 */
    private static volatile boolean IS_RUN = false;

    /** 是否关闭 */
    private static volatile boolean IS_CLOSE = false;

    /** 线程池 */
    private static volatile ThreadPoolExecutor EXECUTOR;

    /** 等待删除的列表 */
    private static final List<String> DELETE_LIST = new ArrayList<>();

    /** 任务列表 */
    private static final List<TaskModel> TASK_LIST = new ArrayList<>();

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eGermStreamTimer.class);

    /**
     * 初始化操作
     */
    public static void init() {
        init(new ThreadPoolExecutor(1, 4,
                300L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory()));
    }

    /**
     * 初始化操作
     * @param executor 线程池
     */
    @SuppressWarnings("all")
    public synchronized static void init(ThreadPoolExecutor executor) {
        if (IS_RUN) {
            throw new RuntimeException(P6eGermStreamTimer.class.getName() + " timer is run. please close it before init");
        } else {
            LOGGER.info(P6eGermStreamTimer.class.getName() + " start create timer.");

            executor.setThreadFactory(new DefaultThreadFactory());
            IS_RUN = true;
            IS_CLOSE = false;
            EXECUTOR = executor;
            EXECUTOR.execute(() -> {
                while (true) {
                    try {
                        // 判断是否推出
                        if (IS_CLOSE) {
                            return;
                        }

                        // 获取需要删除的 ID 数据
                        final String[] deletes;
                        synchronized (DELETE_LIST) {
                            deletes = DELETE_LIST.toArray(new String[0]);
                            DELETE_LIST.clear();
                        }

                        // 当前时间戳
                        final long currentTime = System.currentTimeMillis();

                        // 遍历任务
                        final Iterator<TaskModel> iterator = TASK_LIST.iterator();
                        while (iterator.hasNext()) {
                            // 得到当前任务模型
                            final TaskModel taskModel = iterator.next();

                            // 判断是否需要删除并推出
                            boolean b = true;
                            for (final String delete : deletes) {
                                if (delete.equals(taskModel.id)) {
                                    b = false;
                                    iterator.remove();
                                    break;
                                }
                            }

                            if (b) {
                                // 执行任务
                                if (taskModel == null) {
                                    iterator.remove();
                                } else {
                                    if (currentTime >= taskModel.date + taskModel.interval) {
                                        // 执行任务
                                        if (taskModel.runnable != null) {
                                            EXECUTOR.execute(taskModel.runnable);
                                        }
                                        if (taskModel.isLoop) {
                                            // 更新时间戳
                                            taskModel.date = currentTime;
                                        } else {
                                            // 删除不循环的任务
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                        }

                        // 休眠 500 ms
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            LOGGER.info(P6eGermStreamTimer.class.getName() + " finish create timer.");
        }
    }

    /**
     * 创建定时任务
     * @param runnable 运行任务
     * @param isRun 是否运行
     * @param isLoop 是否循环
     * @param interval 时间间隔
     * @return 创建的 ID
     */
    public synchronized static String create(Runnable runnable, boolean isRun, boolean isLoop, long interval) {
        if (EXECUTOR == null) {
            throw new RuntimeException(P6eGermStreamTimer.class.getName() + " timer not initialized or close.");
        } else {
            final TaskModel taskModel = new TaskModel(runnable, true, interval);
            if (isRun) {
                EXECUTOR.execute(runnable);
                if (isLoop) {
                    TASK_LIST.add(taskModel);
                }
            } else {
                TASK_LIST.add(taskModel);
            }
            // 返回唯一标识符 ID
            return taskModel.id;
        }
    }

    /**
     * 关闭定时任务
     * @param id 需要删除的 ID
     */
    public static void delete(String id) {
        synchronized (DELETE_LIST) {
            DELETE_LIST.add(id);
        }
    }

    /**
     * 关闭服务
     */
    public static void shutdown() {
        // 修改状态
        IS_RUN = false;
        // 关闭轮询线程
        IS_CLOSE = true;
        // 关闭线程池
        if (EXECUTOR != null) {
            EXECUTOR.shutdown();
            EXECUTOR = null;
        }
    }

    /**
     * 修改线程池里面的线程名称
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private DefaultThreadFactory() {
            final SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "timer-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 任务模型
     */
    private static class TaskModel {
        private final Runnable runnable;
        private final boolean isLoop;
        private final long interval;
        private final String id;
        private long date;

        public TaskModel(Runnable runnable, boolean isLoop, long interval) {
            this.runnable = runnable;
            this.isLoop = isLoop;
            this.interval = interval;
            this.date = System.currentTimeMillis();
            this.id = UUID.randomUUID().toString().replaceAll("-", "");
        }
    }
}
