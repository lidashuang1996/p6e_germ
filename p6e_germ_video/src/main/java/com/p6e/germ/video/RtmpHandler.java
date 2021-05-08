package com.p6e.germ.video;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理器
 * @author lidashuang
 * @version 1.0
 */
public class RtmpHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每次收到消息处理的勾子函数
     * @param context 上下文对象
     * @param message 收到的消息内容
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (message instanceof RtmpMessage) {
            final RtmpMessage rtmpMessage = (RtmpMessage) message;
            final RtmpActuator rtmpActuator = RtmpRegistry.getActuatorCore(rtmpMessage.getType());
            System.out.println("处理器为 ==> " + rtmpActuator);
            if (rtmpActuator != null) {
                // 立即执行
                rtmpActuator.execute(new RtmpContext(context), rtmpMessage);
            }
        }

    }

}
