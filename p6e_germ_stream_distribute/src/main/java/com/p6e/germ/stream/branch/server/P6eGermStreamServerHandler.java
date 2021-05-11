package com.p6e.germ.stream.branch.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandler;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eGermStreamServerHandler implements ChannelInboundHandler {

    /**
     * 创建
     */
    protected static void createDispatchObject(P6eGermStreamServerContext context) {
        P6eGermStreamServerDispatcher.create(context);
    }

    /**
     * 删除
     */
    protected static void deleteDispatchObject(P6eGermStreamServerContext context) {
        P6eGermStreamServerDispatcher.delete(context);
    }

    /**
     * 推送数据
     * @param byteBuf 推送数据内容
     */
    public static void pushDataStream(ByteBuf byteBuf) {
        P6eGermStreamServerDispatcher.pushData(byteBuf);
    }

}
