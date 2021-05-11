package com.p6e.germ.stream.branch;

import com.p6e.germ.stream.branch.client.P6eGermStreamClient;
import com.p6e.germ.stream.branch.client.P6eGermStreamClientHandler;
import com.p6e.germ.stream.branch.server.P6eGermStreamServer;
import com.p6e.germ.stream.branch.server.P6eGermStreamServerHandler;

/**
 * 启动器
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGermStreamBranch {

    /** 是否启动 */
    private static boolean IS_RUN = false;

    /** 客户端 地址 */
    private static String CLIENT_HOST = "localhost";

    /** 客户端 端口号 */
    private static int CLIENT_PORT = 11100;

    /** 服务端 端口号 */
    private static int SERVER_PORT = 11100;

    /** 客户端 */
    private static P6eGermStreamClient CLIENT;

    /** 服务端 */
    private static P6eGermStreamServer SERVER;

    /** 客户端 处理器 */
    private static P6eGermStreamClientHandler CLIENT_HANDLER;

    /** 服务端 处理器 */
    private static P6eGermStreamServerHandler SERVER_HANDLER;

    /**
     * 写入客户端对象
     * @param client 客户端对象
     */
    public static void setClient(P6eGermStreamClient client) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            CLIENT = client;
        }
    }

    /**
     * 写入服务端对象
     * @param server 服务端对象
     */
    public static void setServer(P6eGermStreamServer server) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            SERVER = server;
        }
    }

    /**
     * 写入客户端处理对象
     * @param handler 客户端处理对象
     */
    public static void setClientHandler(P6eGermStreamClientHandler handler) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            CLIENT_HANDLER = handler;
        }
    }

    /**
     * 写入服务端处理对象
     * @param handler 服务端处理对象
     */
    public static void setServerHandler(P6eGermStreamServerHandler handler) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            SERVER_HANDLER = handler;
        }
    }

    /**
     * 设置客户端连接地址
     * @param host 地址
     */
    public static void setClientHost(String host) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            CLIENT_HOST = host;
        }
    }

    /**
     * 设置客户端连接端口
     * @param port 端口
     */
    public static void setClientPort(int port) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            CLIENT_PORT = port;
        }
    }

    /**
     * 设置客户端连接地址和连接端口
     * @param host 地址
     * @param port 端口
     */
    public static void setClientHostAndPort(String host, int port) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            CLIENT_HOST = host;
            CLIENT_PORT = port;
        }
    }

    /**
     * 设置服务端暴露端口
     * @param port 端口
     */
    public static void setServerPort(int port) {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            SERVER_PORT = port;
        }
    }

    /**
     * 初始化操作
     */
    public static void init() {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service is started and cannot be modified.");
        } else {
            if (CLIENT == null) {
                if (CLIENT_HANDLER == null) {
                    throw new RuntimeException(
                            P6eGermStreamBranch.class.getName() + " client handler is null.");
                }
                CLIENT = new P6eGermStreamClient(CLIENT_HOST, CLIENT_PORT, CLIENT_HANDLER);
            }
            if (SERVER == null) {
                if (SERVER_HANDLER == null) {
                    throw new RuntimeException(
                            P6eGermStreamBranch.class.getName() + " server handler is null.");
                }
                SERVER = new P6eGermStreamServer(SERVER_PORT, SERVER_HANDLER);
            }
        }
    }

    /**
     * 启动服务
     */
    public synchronized static void startup() {
        if (IS_RUN) {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service started, no need to start again.");
        } else {
            if (CLIENT == null) {
                throw new RuntimeException(P6eGermStreamBranch.class.getName() + " startup() ==> " +
                        "client is null, please initialize [ init() ] or write client object before [ setClient() ]");
            }
            if (SERVER == null) {
                throw new RuntimeException(P6eGermStreamBranch.class.getName() + " startup() ==> " +
                        "server is null, please initialize [ init() ] or write client object before [ setServer() ]");
            }
            CLIENT.startup();
            SERVER.startup();

            if (P6eGermStreamTimer.IS_DEFAULT_RUN) {
                P6eGermStreamTimer.init();
            }

            IS_RUN = true;
        }
    }

    /**
     * 关闭服务
     */
    public synchronized static void shutdown() {
        if (IS_RUN) {
            if (CLIENT != null) {
                CLIENT.shutdown();
                CLIENT = null;
            }
            if (SERVER != null) {
                SERVER.shutdown();
                SERVER = null;
            }
            P6eGermStreamTimer.shutdown();
            IS_RUN = false;
        } else {
            throw new RuntimeException(
                    P6eGermStreamBranch.class.getName() + " service not started, no need to shut down.");
        }
    }
}
