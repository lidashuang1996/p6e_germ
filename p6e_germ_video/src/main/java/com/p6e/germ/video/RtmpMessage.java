package com.p6e.germ.video;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class RtmpMessage {

    public static class Header {
        /**
         *  0 1 2 3 4 5 6 7
         * +-+-+-+-+-+-+-+-+
         * |fmt|   cs id   |
         * +-+-+-+-+-+-+-+-+
         *
         *  0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7
         * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         * |fmt|    0      |  cs id - 64   |
         * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         *
         *  0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7
         * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         * |fmt|    1      |          cs id - 64           |
         * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         *
         * RTMP协议支持用户自定义 [3,65599] 之间的 CSID，0, 1, 2 由协议保留表示特殊信息。
         * 0 代表 Basic Header 总共要占用 2 个字节，CSID 在 [64,319] 之间;
         * 1 代表占用 3 个字节，CSID 在 [64,65599] 之间;
         * 2 代表该 chunk 是控制信息和一些命令信息。
         *
         */
        private final int fmt;
        private final int csId;

        /**
         * fmt 0：11 bytes
         * fmt 1：7 bytes
         * fmt 2：3 bytes
         */

        private final int timestamp;
        private final int messageLength;
        private final int messageTypeId;
        private final int messageStreamId;

        public Header(int fmt, int csId, int timestamp, int messageLength, int messageTypeId, int messageStreamId) {
            this.fmt = fmt;
            this.csId = csId;
            this.timestamp = timestamp;
            this.messageLength = messageLength;
            this.messageTypeId = messageTypeId;
            this.messageStreamId = messageStreamId;
        }

        public int getFmt() {
            return fmt;
        }

        public int getCsId() {
            return csId;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public int getMessageLength() {
            return messageLength;
        }

        public int getMessageTypeId() {
            return messageTypeId;
        }

        public int getMessageStreamId() {
            return messageStreamId;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"fmt\":"
                    + fmt
                    + ",\"csId\":"
                    + csId
                    + ",\"timestamp\":"
                    + timestamp
                    + ",\"messageLength\":"
                    + messageLength
                    + ",\"messageTypeId\":"
                    + messageTypeId
                    + ",\"messageStreamId\":"
                    + messageStreamId
                    + "}";
        }
    }

    private Header header;

    /**
     * 获取消息类型
     * @return 消息类型
     */
    public abstract int getType();

    /**
     * 编码 [消息头 + 消息内容]
     * @return 编码后内容的字节码
     */
    public abstract byte[] encode();

    /**
     * 解码 [消息头 + 消息内容]
     * @param bytes 解码的参数
     * @return 解码后的消息对象
     */
    public abstract RtmpMessage decode(Header header, byte[] bytes);


}
