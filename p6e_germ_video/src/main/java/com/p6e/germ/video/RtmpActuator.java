package com.p6e.germ.video;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface RtmpActuator {

    /**
     * 收到消息后执行的方法
     * @param context Netty 上下文对象
     * @param message Jt 消息对象
     */
    public void execute(RtmpContext context, RtmpMessage message);

}
