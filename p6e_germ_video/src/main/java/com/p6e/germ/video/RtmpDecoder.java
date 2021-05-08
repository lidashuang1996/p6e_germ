package com.p6e.germ.video;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RTMP 协议的解码器
 * @author lidashuang
 * @version 1.0
 */
public class RtmpDecoder extends ByteToMessageDecoder {

    public static boolean b = false;

    /**
     * RTMP 握手协议对象
     */
    private final RtmpHandshake handshake;

    /**
     * 分包长度
     */
    private byte[] subpackage = null;
    private int subpackageIndex = 0;
    private int subpackageLength = 0;

    /**
     * 缓存的上一次的消息的头部
     */
    private RtmpMessage.Header cacheHeader = null;

    /**
     * 默认构造函数
     */
    public RtmpDecoder() {
        this.handshake = new RtmpHandshake();
    }

    /**
     * 默认的构造函数，自定义 RTMP 握手协议对象
     * @param handshake RTMP 握手协议对象
     */
    public RtmpDecoder(RtmpHandshake handshake) {
        this.handshake = handshake;
    }

    int a = 0;
    ByteArrayOutputStream byteArrayOutputStream = null;

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 读取数据
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("收到数据 --->  "  + Utils.bytesToHex(bytes));

        if (b) {
            return;
        }

        // 判断握手是否成功
        if (this.handshake.isSuccess()) {
            list.addAll(this.decodeBytesToMessage(bytes));
        } else {
            // 字节码发送给握手的对象
            final byte[] result = this.handshake.execute(context, bytes);
            // 不为空表示存在需要解析的消息
            if (result != null) {
                // 解析消息
                list.addAll(this.decodeBytesToMessage(result));
            }
        }
    }

    private boolean is = true;

    /**
     * 解码字节码转为消息对象列表
     * @param bytes 字节码对象
     * @return 消息对象列表
     */
    @SuppressWarnings("all")
    public List<RtmpMessage> decodeBytesToMessage(byte[] bytes) {
        final List<RtmpMessage> result = new ArrayList<>();
        try {
            if (bytes != null) {
                int index = 0;
                // 是否分包
                if (subpackageLength > 0) {
                    if (cacheHeader == null) {
                        throw new RuntimeException("[ 解码失败 ] 不存在之前的头文件 ～");
                    } else {
                        final int interval = subpackageLength - subpackageIndex;
                        if (bytes.length >= interval) {
                            System.arraycopy(bytes, 0, subpackage, subpackageIndex, interval);
                            result.add(createRtmpMessage(subpackage));
                            result.addAll(decodeBytesToMessage(Utils.bytesArrayIntercept(bytes, interval, bytes.length - interval)));
                            subpackage = null;
                            subpackageIndex = 0;
                            subpackageLength = 0;
                        } else {
                            System.arraycopy(bytes, 0, subpackage, subpackageIndex, bytes.length);
                            subpackageIndex = subpackageIndex + bytes.length;
                        }
                    }
                } else {
                    while (index + 1 < bytes.length) {
                        int csId;
                        final int ct = bytes[index] & 0x3F;
                        final int fmt = (bytes[index] >> 6) & 0xFF;

                        switch (ct) {
                            case 0x00:
                                csId = 64 + bytes[++index];
                                break;
                            case 0x3F:
                                index += 2;
                                csId = 64 + bytes[++index] + 256 * bytes[++index];
                                break;
                            default:
                                csId = ct;
                                break;
                        }
                        final int timestamp, messageLength, messageTypeId, messageStreamId;
                        switch (fmt) {
                            case 0:
                                if (index + 11 < bytes.length) {
                                    timestamp = Utils.bytesToIntLittle(new byte[] {
                                            0,
                                            bytes[++index],
                                            bytes[++index],
                                            bytes[++index],
                                    });
                                    messageLength = Utils.bytesToIntLittle(new byte[] {
                                            0,
                                            bytes[++index],
                                            bytes[++index],
                                            bytes[++index],
                                    });
                                    messageTypeId = bytes[++index];
                                    messageStreamId = Utils.bytesToIntBig(new byte[] {
                                            bytes[++index],
                                            bytes[++index],
                                            bytes[++index],
                                            bytes[++index],
                                    });
                                } else {
                                    throw new RuntimeException("[ 解码失败 ] 解析头文部数据，消息异常 ～");
                                }
                                break;
                            case 1:
                                if (index + 7 < bytes.length) {
                                    if (cacheHeader == null) {
                                        throw new RuntimeException("[ 解码失败 ] 不存在之前的头文件 ～");
                                    } else {
                                        timestamp = Utils.bytesToIntLittle(new byte[] {
                                                0,
                                                bytes[++index],
                                                bytes[++index],
                                                bytes[++index],
                                        });
                                        messageLength = Utils.bytesToIntLittle(new byte[] {
                                                0,
                                                bytes[++index],
                                                bytes[++index],
                                                bytes[++index],
                                        });
                                        messageTypeId = bytes[++index];
                                        messageStreamId = cacheHeader.getMessageStreamId();
                                    }
                                } else {
                                    throw new RuntimeException("[ 解码失败 ] 解析头文部数据，消息异常 ～");
                                }
                                break;
                            case 2:
                                if (index + 3 < bytes.length) {
                                    if (cacheHeader == null) {
                                        throw new RuntimeException("[ 解码失败 ] 不存在之前的头文件 ～");
                                    } else {
                                        timestamp = Utils.bytesToIntLittle(new byte[] {
                                                0,
                                                bytes[++index],
                                                bytes[++index],
                                                bytes[++index],
                                        });
                                        messageLength = cacheHeader.getMessageLength();
                                        messageTypeId = cacheHeader.getMessageTypeId();
                                        messageStreamId = cacheHeader.getMessageStreamId();
                                    }
                                } else {
                                    throw new RuntimeException("[ 解码失败 ] 解析头文部数据，消息异常 ～");
                                }
                                break;
                            case 3:
                                if (cacheHeader == null) {
                                    throw new RuntimeException("[ 解码失败 ] 不存在之前的头文件 ～");
                                } else {
                                    timestamp = cacheHeader.getTimestamp();
                                    messageLength = cacheHeader.getMessageLength();
                                    messageTypeId = cacheHeader.getMessageTypeId();
                                    messageStreamId = cacheHeader.getMessageStreamId();
                                }
                                break;
                            default:
                                throw new RuntimeException("[ 解码失败 ] FMT 异常 ～");
                        }
                        cacheHeader = new RtmpMessage.Header(fmt, csId, timestamp, messageLength, messageTypeId, messageStreamId);
                        System.out.println(index + messageLength + 1 <= bytes.length);
                        if (index + messageLength + 1 <= bytes.length) {
                            if (is) {
                                byte[] cs = Utils.bytesArrayIntercept(bytes, index + 1, bytes.length - index - 1);
                                for (int i = cs.length - 1; i >= 0; i--) {
                                    if (cs[i] == -61) {
                                        System.arraycopy(cs, 0, cs, 0, i);
                                        System.arraycopy(cs, i + 1, cs, i, cs.length - i - 1);
                                        cs = Arrays.copyOf(cs, cs.length - 1);
                                        break;
                                    }
                                }
                                result.add(createRtmpMessage(cs));
                                index = bytes.length - 1;
                            } else {
                                result.add(createRtmpMessage(Utils.bytesArrayIntercept(bytes, index + 1, messageLength)));
                                index = index + messageLength + 1;
                            }
                        } else {
                            subpackageLength = messageLength;
                            subpackage = new byte[subpackageLength];
                            System.arraycopy(bytes, index + 1, subpackage, 0, bytes.length - index - 1);
                            subpackageIndex = bytes.length - index - 1;

                            index = bytes.length;
                        }
                        is = false;
                    }
                }
            } else {
                // 43 // 00 00 00 // 00 00 1e // 14 // 02 00 0d 72 // 656c6561736553747265616d00400000000000000005020001314300000000001a1402000946435075626c6973680040080000000000000502000131430000000000191402000c63726561746553747265616d00401000000000000005
                throw new RuntimeException("[ 解码失败 ] 字节码长度异常 ～");
            }
        } catch (Exception e) {
            subpackage = null;
            subpackageIndex = 0;
            subpackageLength = 0;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建 RTMP 消息的对象
     * @return 消息的对象
     */
    private RtmpMessage createRtmpMessage(byte[] bytes) {
        return RtmpRegistry.getMessageCore(cacheHeader.getMessageTypeId()).decode(cacheHeader, bytes);
    }
}
