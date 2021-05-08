package com.p6e.germ.video;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpContext {

    /** netty 的上下文 */
    private final ChannelHandlerContext context;

    /**
     * 构造输入 netty 的上下文
     * @param context netty 上下文参数对象
     */
    public RtmpContext(ChannelHandlerContext context) {
        this.context = context;
    }

    /**
     * 推送消息对象
     * @param message 消息对象
     */
    public void sendMessage(RtmpMessage message) {
        if (context != null) {
            final Channel channel = context.channel();
            if (channel != null) {
                // 得到消息编码后的内容并发送出去
                channel.writeAndFlush(message);
            }
        }
    }

    public void writeAndFlush(Object o) {
        if (context != null) {
            final Channel channel = context.channel();
            if (channel != null) {
                // 得到消息编码后的内容并发送出去
                channel.writeAndFlush(o);
            }
        }
    }
}
