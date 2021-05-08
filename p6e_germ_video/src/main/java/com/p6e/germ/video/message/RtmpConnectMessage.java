package com.p6e.germ.video.message;

import com.p6e.germ.video.RtmpMessage;
import com.p6e.germ.video.Utils;

import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpConnectMessage extends RtmpMessage {

    public static final int M_ID = 0x14;

    private Header header;
    private byte[] bytes;

    private Object[] data;

    public RtmpConnectMessage() {
    }

    public RtmpConnectMessage(Header header, byte[] bytes) {
        this.header = header;
        this.bytes = bytes;
    }

    public RtmpConnectMessage(Header header, Object[] data, byte[] bytes) {
        this.header = header;
        this.data = data;
        this.bytes = bytes;
    }

    @Override
    public int getType() {
        return M_ID;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public RtmpMessage decode(Header header, byte[] bytes) {
        // AMF0 解码
        return new RtmpConnectMessage(header, Utils.amfDecoder(bytes), bytes);
    }

    @Override
    public String toString() {
        return "{"
                + "\"header\":"
                + header
                + ",\"bytes\":"
                + Arrays.toString(bytes)
                + ",\"data\":"
                + Arrays.toString(data)
                + "}";
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Object[] getData() {
        return data;
    }
}
