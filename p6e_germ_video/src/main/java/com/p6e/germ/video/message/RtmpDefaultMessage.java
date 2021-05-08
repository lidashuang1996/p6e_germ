package com.p6e.germ.video.message;

import com.p6e.germ.video.RtmpMessage;

import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpDefaultMessage extends RtmpMessage {

    private Header header;
    private byte[] bytes;

    public RtmpDefaultMessage() { }

    public RtmpDefaultMessage(Header header, byte[] bytes) {
        this.header = header;
        this.bytes = bytes;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public RtmpMessage decode(Header header, byte[] bytes) {
        return new RtmpDefaultMessage(header, bytes);
    }

    @Override
    public String toString() {
        return "{"
                + "\"header\":"
                + header
                + ",\"bytes\":"
                + Arrays.toString(bytes)
                + "}";
    }
}
