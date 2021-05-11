package com.p6e.germ.stream.branch.client;

import com.p6e.germ.stream.branch.P6eGermStream;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端启动器
 * @author lidashuang
 * @version 1.0
 */
public class P6eGermStreamClient implements P6eGermStream {

    /** 服务端处理器的名称 */
    public static final String CHANNEL_HANDLER_NAME = "P6E_GERM_STREAM_BRANCH_CLIENT";

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eGermStreamClient.class);

    /** 服务启动的端口 */
    private String host = "localhost";

    /** 服务启动的端口 */
    private int port = 11100;

    /** 启动器 */
    private Bootstrap client;

    /** IO 处理方式 */
    private EventLoopGroup group;

    /** 处理器 */
    private final P6eGermStreamClientHandler handler;

    /**
     * 构造方法
     * @param handler 处理器
     */
    public P6eGermStreamClient(P6eGermStreamClientHandler handler) {
        this.handler = handler;
    }

    public P6eGermStreamClient(String host, int port, P6eGermStreamClientHandler handler) {
        this.host = host;
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void startup() {
        try {
            LOGGER.info(P6eGermStreamClient.class.getName() + " start startup.");

            client = new Bootstrap();
            group = new NioEventLoopGroup();

            // 创建客户端对象
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) {
                    final ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(CHANNEL_HANDLER_NAME, handler);
                }
            });

            // 绑定端口，启动客户端服务
            client.connect(host, port).sync();

            LOGGER.info(P6eGermStreamClient.class.getName() + " finish startup.");
            LOGGER.info(P6eGermStreamClient.class.getName() + " client connect info ==> host: " + host + ", port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
            shutdown();
        }
    }

    @Override
    public void shutdown() {
        try {
            LOGGER.info(P6eGermStreamClient.class.getName() + " start shutdown.");

            // 关闭组
            if (group != null) {
                group.shutdownGracefully();
            }
            // 关闭客户端
            if (client != null) {
                client.clone();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group = null;
            client = null;

            LOGGER.info(P6eGermStreamClient.class.getName() + " finish shutdown.");
        }
    }
}
