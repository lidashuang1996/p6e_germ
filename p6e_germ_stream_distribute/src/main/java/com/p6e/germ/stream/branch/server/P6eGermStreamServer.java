package com.p6e.germ.stream.branch.server;

import com.p6e.germ.stream.branch.P6eGermStream;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端启动器
 * @author lidashuang
 * @version 1.0
 */
public class P6eGermStreamServer implements P6eGermStream {

    /** 服务端处理器的名称 */
    public static final String CHANNEL_HANDLER_NAME = "P6E_GERM_STREAM_BRANCH_SERVER";

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eGermStreamServer.class);

    /** 服务启动的端口 */
    private int port = 11100;

    /** 启动器 */
    private ServerBootstrap server;

    /** 连接处理方式 */
    private EventLoopGroup bossGroup;

    /** IO 处理方式 */
    private EventLoopGroup workerGroup;

    /** 处理器 */
    private final P6eGermStreamServerHandler handler;

    /**
     * 构造方法
     * @param handler 处理器
     */
    public P6eGermStreamServer(P6eGermStreamServerHandler handler) {
        this.handler = handler;
    }

    /**
     * 构造方法
     * @param port 端口
     * @param handler 处理器
     */
    public P6eGermStreamServer(int port, P6eGermStreamServerHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void startup() {
        try {
            LOGGER.info(P6eGermStreamServer.class.getName() + " start startup.");

            server = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            // 创建服务端对象
            server.group(bossGroup, workerGroup);
            server.channel(NioServerSocketChannel.class);
            server.childHandler(new ChannelInitializer<ServerSocketChannel>() {
                @Override
                protected void initChannel(ServerSocketChannel channel) {
                    final ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(CHANNEL_HANDLER_NAME, handler);
                }
            });


            // 初始化调度员
            P6eGermStreamServerDispatcher.init();

            // 绑定端口，启动服务端服务
            server.bind(port).sync();

            LOGGER.info(P6eGermStreamServer.class.getName() + " finish startup.");
            LOGGER.info(P6eGermStreamServer.class.getName() + " server bind info ==> port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
            shutdown();
        }
    }

    @Override
    public void shutdown() {
        try {
            LOGGER.info(P6eGermStreamServer.class.getName() + " start shutdown.");

            // 关闭组
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            // 关闭客户端
            if (server != null) {
                server.clone();
            }

            // 关闭调度员
            P6eGermStreamServerDispatcher.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server = null;
            bossGroup = null;
            workerGroup = null;

            LOGGER.info(P6eGermStreamServer.class.getName() + " finish shutdown.");
        }
    }

}
