package com.p6e.germ.stream.branch.client;

import com.p6e.germ.stream.branch.server.P6eGermStreamServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandler;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eGermStreamClientHandler implements ChannelInboundHandler {

    /**
     * 推送数据流
     * 推送数据流给所有在线客户端
     */
    protected static void pushDataStream(ByteBuf byteBuf) {
        P6eGermStreamServerHandler.pushDataStream(byteBuf);
    }

}
