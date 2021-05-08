package com.p6e.germ.video;

import com.p6e.germ.video.actuator.RtmpConnectActuator;
import com.p6e.germ.video.actuator.RtmpDefaultActuator;
import com.p6e.germ.video.message.RtmpConnectMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ./ffmpeg -re -i name2.mp4 -c copy -f flv rtmp://127.0.0.1:19000/live/1
 * 视屏推送服务
 * @author lidashuang
 * @version 1.0
 */
@SpringBootApplication
public class VideoApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(VideoApplication.class, args);
//    }

    public static void main(String[] args) {

        RtmpRegistry.registerMessage(RtmpConnectMessage.M_ID, new RtmpConnectMessage());
        RtmpRegistry.registerActuator(RtmpConnectMessage.M_ID, new RtmpConnectActuator());

        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Worker线程
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Netty服务
            // ServerBootstrap   ServerSocketChannel
            final ServerBootstrap server = new ServerBootstrap();
            // 链路式编程
            server.group(bossGroup, workerGroup)
                    // 主线程处理类,看到这样的写法，底层就是用反射
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 在管道中添加处理器
                            channel.pipeline()
//                                    .addLast(new RtmpServerCodec())
                                    .addLast(new RtmpDecoder())
                                    .addLast(new RtmpEncoder()).addLast(new RtmpHandler());
                        }
                    }) .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024);
            // 启动服务器
            final ChannelFuture channelFuture = server.bind(19000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
