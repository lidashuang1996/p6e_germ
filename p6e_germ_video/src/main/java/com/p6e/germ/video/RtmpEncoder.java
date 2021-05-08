package com.p6e.germ.video;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpEncoder extends MessageToByteEncoder<RtmpMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RtmpMessage o, ByteBuf byteBuf) throws Exception {

    }

}
