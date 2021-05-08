package com.p6e.germ.video;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * RTMP 协议的服务端 编码/解码 器
 * @author lidashuang
 * @version 1.0
 */
public class RtmpServerCodec extends CombinedChannelDuplexHandler<RtmpDecoder, RtmpEncoder> {

    public RtmpServerCodec() {
        super(new RtmpDecoder(), new RtmpEncoder());
    }

    public RtmpServerCodec(RtmpDecoder decoder, RtmpEncoder encoder) {
        super(decoder, encoder);
    }

}
