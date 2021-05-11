package com.p6e.germ.stream.branch.client;

import com.p6e.germ.stream.branch.P6eGermStreamTimer;
import com.p6e.germ.stream.branch.P6eGermStreamUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 默认的 socket 客户端处理器
 * @author lidashuang
 * @version 1.0
 */
public class P6eGermStreamClientSocketDefaultHandler extends P6eGermStreamClientHandler {

    /** 注入的日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eGermStreamClientSocketDefaultHandler.class);

    /**
     * 连接消息
     *
     * 000 00000   000 版本号 [001] // 00000 消息类型  [ 00000 关闭 00001 连接 00010 认证 00011 心跳 ]
     * 00000000 00000000 高8 低8  消息长度
     * .... 消息内容
     */
    private static final String CONNECT_MESSAGE = "21";

    /** 认证消息 */
    private static final String AUTH_MESSAGE = "22";

    /** 心跳消息 */
    private static final String HEARTBEAT_MESSAGE = "23";

    /** 关闭消息 */
    private static final String CLOSE_MESSAGE = "20";

    /** 认证的令牌 */
    private final String token;

    /**
     * 构造方法
     * TOKEN 令牌
     * @param token 令牌
     */
    public P6eGermStreamClientSocketDefaultHandler(String token) {
        if (token == null) {
            throw new RuntimeException(
                    P6eGermStreamClientSocketDefaultHandler.class.getName() + " token is null.");
        } else {
            this.token = token;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= handlerAdded =============");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= handlerRemoved =============");
        context.channel().writeAndFlush(Unpooled.copiedBuffer(P6eGermStreamUtils.hexToByte(CLOSE_MESSAGE)));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= channelRegistered =============");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= channelUnregistered =============");
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= channelActive =============");
        // 发送连接消息
        context.channel().writeAndFlush(Unpooled.copiedBuffer(P6eGermStreamUtils.hexToByte(CONNECT_MESSAGE)));
        // 发送认证消息
        final byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        context.channel().writeAndFlush(Unpooled.copiedBuffer(P6eGermStreamUtils.hexToByte(
                AUTH_MESSAGE
                + P6eGermStreamUtils.bytesToHex(P6eGermStreamUtils.intToHigh8Low8(tokenBytes.length))
                + P6eGermStreamUtils.bytesToHex(tokenBytes)
        )));
        // 发送心跳消息
        P6eGermStreamTimer.create(() -> context.channel().writeAndFlush(Unpooled.copiedBuffer(
                P6eGermStreamUtils.hexToByte(HEARTBEAT_MESSAGE))), false, true, 45000);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= channelInactive =============");
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object o) throws Exception {
        if (o instanceof ByteBuf) {
            pushDataStream((ByteBuf) o);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception { }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object o) throws Exception {
        LOGGER.info("============= userEventTriggered =============> " + o);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext context) throws Exception {
        LOGGER.info("============= channelWritabilityChanged =============");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) throws Exception {
        LOGGER.info("============= exceptionCaught =============");
    }

}
