package com.p6e.germ.stream.branch.server;

import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流分发的调度员
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGermStreamServerDispatcher {

    /** 是否初始化 */
    private static volatile boolean IS_INIT = false;

    /** 处理数据的线程数 */
    private static volatile int THREAD_LENGTH = 5;

    /** 调度的线程池 */
    private static volatile ThreadPoolExecutor EXECUTOR;

    /** 管道的列表 */
    private static final Channel[] CHANNEL_LIST = new Channel[THREAD_LENGTH];

    /**
     * 初始化
     */
    public static void init() {
        for (int i = 0; i < THREAD_LENGTH; i++) {
            CHANNEL_LIST[i] = new Channel(i);
        }
        EXECUTOR = new ThreadPoolExecutor(THREAD_LENGTH, THREAD_LENGTH,
                300L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory());
        IS_INIT = true;
    }

    /**
     * 关闭
     */
    public static void shutdown() {
        Arrays.fill(CHANNEL_LIST, null);
        if (EXECUTOR != null) {
            EXECUTOR.shutdown();
            EXECUTOR = null;
        }
    }

    /**
     * 创建
     */
    public static void create(P6eGermStreamServerContext context) {
        if (IS_INIT) {
            CHANNEL_LIST[0].create(context);
        } else {
            throw new RuntimeException(P6eGermStreamServerDispatcher.class.getName() + " no init.");
        }
    }

    /**
     * 删除
     */
    public static void delete(P6eGermStreamServerContext context) {
        if (IS_INIT) {
            for (final Channel channel : CHANNEL_LIST) {
                channel.delete(context);
            }
        } else {
            throw new RuntimeException(P6eGermStreamServerDispatcher.class.getName() + " no init.");
        }
    }

    /**
     * 推送数据流
     * @param byteBuf 数据流
     */
    public static void pushData(ByteBuf byteBuf) {
        if (IS_INIT) {
            for (final Channel channel : CHANNEL_LIST) {
                if (channel.getLength() > 0) {
                    channel.pushData(byteBuf);
                }
            }
        } else {
            throw new RuntimeException(P6eGermStreamServerDispatcher.class.getName() + " no init.");
        }
    }

    /**
     * 获取管道对象列表
     * @return 管道对象列表
     */
    public static Channel[] getChannelList() {
        return CHANNEL_LIST;
    }

    /**
     * 设置线程长度
     * @param length 线程长度
     */
    public static void setThreadLength(int length) {
        THREAD_LENGTH = length;
    }

    /**
     * 获取线程长度
     * @return 获取线程长度
     */
    public static int getThreadLength() {
        return THREAD_LENGTH;
    }

    /**
     * 线程执行分发
     */
    private static class Channel {

        /** 索引 */
        private final int index;

        /** 当前长度 */
        private int length = 0;

        /** 容器保存上下文，默认长度为 200 */
        private P6eGermStreamServerContext[] container = new P6eGermStreamServerContext[200];

        /** 构造方法 */
        public Channel(int index) {
            this.index = index;
        }

        /** 获取索引 */
        public int getIndex() {
            return index;
        }

        /** 获取当前长度 */
        public int getLength() {
            return length;
        }

        /** 创建 */
        public void create(P6eGermStreamServerContext context) {
            synchronized(this) {
                if (length < container.length) {
                    for (int i = 0; i < container.length; i++) {
                        if (container[i] == null) {
                            container[i] = context;
                            break;
                        }
                    }
                } else {
                    // 扩容
                    final int oldContainerLength = container.length;
                    container = Arrays.copyOf(container, container.length + 200);
                    // 赋值
                    container[oldContainerLength] = context;
                }
                // 长度
                length ++;
            }
        }

        /** 删除 */
        public void delete(P6eGermStreamServerContext context) {
            synchronized(this) {
                for (int i = 0; i < container.length; i++) {
                    if (container[i] != null && container[i] == context) {
                        container[i] = null;
                        // 长度
                        length --;
                        break;
                    }
                }
            }
        }

        /**
         * 推送数据
         */
        public void pushData(ByteBuf byteBuf) {
            if (EXECUTOR == null) {
                throw new RuntimeException(P6eGermStreamServerDispatcher.class.getName() + " executor is null.");
            } else {
                EXECUTOR.execute(() -> {
                    for (final P6eGermStreamServerContext context : container) {
                        context.writeAndFlush(byteBuf);
                    }
                });
            }
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
            namePrefix = "dispatcher-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
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

}
