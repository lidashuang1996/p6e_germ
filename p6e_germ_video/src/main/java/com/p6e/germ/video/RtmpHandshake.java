package com.p6e.germ.video;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 *
 * RTMP 握手阶段
 *
 * 握手以客户端发送 C0 和 C1 块开始。
 * 客户端必须等待接收到 S1 才能发送 C2。
 * 客户端必须等待接收到 S2 才能发送任何其他数据。
 * 服务器端必须等待接收到 C0 才能发送 S0 和 S1，也可以等待接收到 C1 再发送 S0 和 S1。
 * 服务器端必须等待接收到 C1 才能发送 S2。
 * 服务器端必须等待接收到 C2 才能发送任何其他数据。
 *
 * @author lidashuang
 * @version 1.0
 */
public final class RtmpHandshake {

    /** C0/S0 消息的长度 */
    private static final int CS0_LENGTH = 1;
    /** C1/S1 消息的长度 */
    private static final int CS1_LENGTH = 1536;
    /** C2/S2 消息的长度 */
    private static final int CS2_LENGTH = 1536;

    /** RTMP 服务器的版本 */
    private static final int S_VERSION = 3;

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(RtmpHandshake.class);

    private static final byte[] R_BYTES = Utils.hexToByte("F778551ECEAB8E1E362F07C5868A70B266D40220E5086118A22E3697D5FC2B6329C22EE7C7C9B0B5B345DFA8398FEA3BC879208A1068425AA1174CB57258CFEBE24DA42EC4FF9DA19C7B406F18C95BFDD04EE48B6F833214A47B8BF68EB05254E25AE9A9A8B496C5496F6E9CD7C68CB6BCC84169E67F3E372FFE186DA8513862C5AFDEB5A1218A860BACCDD869B253DBAB8A0EFB59A48C442099875E55B74FD07A1B69AF303E099873CB77E2D9E2C4741EB1F506A7F0B2962AF91F5051C890A320994F4C8B36A01FA9C0F15F4B7CF7B6C3817DA60918611D3B02FF7CC94038937537D3382E416BD57577834DF9CAF748352150CE92D0DBB40E871FC364E18B7E3569E39876C1AE0CB79FF6D7AB32F9F011869C8DA4191EF46D3C1BC6B72D83812F2243D1752A412632D66A2075F5D302F45CDF2B98B802B79805DC4837D84EDED1CC42CBA7EEC3137BF86DD0B2D7F624D521A3FF655BCF0DF4101341D230E94651628E45A7ABF8183101CE981E5E86A6FAEC5C79AA7EC7181EA459ED966E0C932A6F9E4BF554AB7B1C905CDE054ED40D2FB89BBFC65752E58CFDE4F2A04DC3AD1E5EF4FC64D57B071C14146A451F6A043156D2880F5957D78DFD3FF2B880F624AC70F27094F37734EAF1924F66AC3D648AE232930544A4420B65EED4696F7F50FE05FC948516650BFB9B65F953D848E3456965D0D76B627A84E35E9DDABD8B43F0FADAEE458CDF59EBD773922E0AE507D5E64092E90EC67ED8439E8F221C753A9F9216F1CB85B5107BC7CC7C4FB2C3EED1AFC709B192F50DE69C1FFCA5650EFB523B5FAB5997CAC4B7EF5AF2BF5973E6293BAE1FC1DA499BE5515077DE4DDB3F7FBCD741711C62B7D803B8D054CF578182714D1C445BD692BA1B839A09E7266FC85DAA8FAD433DABBD206CDE1EA571770C6BFA7956C266EE8435EB01CAA6F583C0D83C33EEFCF3F97DB917BDB299F8A89FB8860AB1CD644C1E82CCE1323A9EB79D25F5DC570B70B40BB20061743353D0536ECDABD82F2277756228CB54FE6932C4C9BD130D2BFC700BF5D85DA6946E96F82E0AFD8E9C20CFEABF9A6A189BB17BBEF539D1D9E73EE966FCB5D6BDBF98444DE865BE1605E34087B34CDF04E3AFF55BD4137C522D2E3C7BF2634DB326B22A474DB7F8A0952AB3510DE555396C18A378A8390B73BFF7772FB2760FA5786A3B0459C45A1D1B0DFF4CBBB65DA0ED34F32116C3C2CC2B95825111BCFBB246C1505AA00C970D06D76CC950CE368B220DB20487CBA02BB1FC54B36567B42913D6B885062CD7795601D31A4A8E6D56467FA5C52162676FD526122513C3E47F2E6D9D6A19C4F8795431A94CED039221D27AC68A7A770E9305252271459B71D05505C4977B70E96851208BACDCDF7E682B3848840880F1FEC2A32FB1406862BC83CAD89ADF729F3BA6FFD3FE808E8403E7C6A0F6DCDA3211898F6F16505B078CC78B80DB600DED2F200F25FF5DCB31D32CBE2056B8BDC31091631ABBD2AC28D61FE32480EC5035BBF35F173393F0D59A21D2300B2DB4B59EAA63FFE0F93D65DC028516905F22A9C7DFF387C4EB78C6300E7EBBB2A6663CFBE5A9977CAF5764BA3F0ABA57DB4E6051E0AEC6E05B1BDAA649A97875EDBC40BCC4214BB7FEF72730D4368ACBD3E10AB4C7642A1C53F93CA26DB9C766555BE26FDFF0E2A721425B95762B0B776FD3E3EB17313D53129C9F34003C99A5196A8C6AACB6D64B51DD98B27C5B59A70BD941A1BA0300FB744CFC499D997E6D93392D28A767D116DFBC2CB9EB40540D11EB304AD9322386FDC597D55D3D1C67BC54DF35479AFB19EA2CD204DD331EFB49C1E172F6BB89FBF58207DCAE6C12F8A7598BEF1E872C0C35F22B4C29DF10F9BB1ADD3AEB887AB5FB38C036B88346F761C42F295DFE0B437DFB4F30A71276AB7D409CE424963F9EBB4F1F47E52B18786F279B5A87226F66981DD6D0AD11F49AF31055FF69710FDA3457C4B7852DB2E3936D0778BF879F1734AD3A24FFB1AE9584760B9CB0452A53BA0962FFE2F605AF52830BD211A5488894CC0B052255048711CD198510A9E943BF8B839198455FBD41073005D303990B88D9B63656D43CFEC8ED83748F4B0F0FC5120216794B22A054E5BC58ABD8C41096070884393453CE509694AFBEABE0");

    public boolean isSuccess() {
        return step == Step.S2;
    }

    private enum Step {
        C0,
        C1,
        C2,
        S0,
        S1,
        S2;
    }

    /** 步骤 */
    private Step step = Step.C0;
    /** 版本号 */
    private int version;
    /** 时间 */
    private int time;
    /** 输入随机数 */
    private String inputRandom;
    /** 输出随机数 */
    private String outputRandom;

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    /**
     * C0 和 S0 的格式
     * C0 和 S0 包都是一个单一的八位字节，以一个单独的八位整型域进行处理：
     *
     *  0 1 2 3 4 5 6 7
     * +-+-+-+-+-+-+-+-+
     * +    version    +
     * +-+-+-+-+-+-+-+-+
     *
     * 以下是 C0/S0 包中的字段：
     * 版本号 (八位)：
     * 在 C0 中，这一字段指示出客户端要求的 RTMP 版本号。
     * 在 S0 中，这一字段指示出服务器端选择的 RTMP 版本号。
     * 本文档中规范的版本号为 3。
     * 0、1、2 三个值是由早期其他产品使用的，是废弃值；4 - 31 被保留为 RTMP 协议的未来实现版本使用。
     * 32 - 255 不允许使用 (以区分开 RTMP 和其他常以一个可打印字符开始的文本协议)。
     * 无法识别客户端所请求版本号的服务器应该以版本 3 响应，(收到响应的) 客户端可以选择降低到版本 3，或者放弃握手。
     *
     *
     * C1 和 S1 的格式
     * C1 和 S1 数据包的长度都是 1536 字节，包含以下字段：
     *
     * Time (四个字节)：这个字段包含一个 timestamp，用于本终端发送的所有后续块的时间起点。
     * 这个值可以是 0，或者一些任意值。要同步多个块流，终端可以发送其他块流当前的 timestamp 的值。
     *
     * Zero (四个字节)：这个字段必须都是 0。
     *
     * Random data (1528 个字节)：这个字段可以包含任意值。
     * 终端需要区分出响应来自它发起的握手还是对端发起的握手，这个数据应该发送一些足够随机的数。
     * 这个不需要对随机数进行加密保护，也不需要动态值。
     *
     */
    public byte[] execute(ChannelHandlerContext context, byte[] bytes) throws Exception {
        System.out.println("握手 --> " + bytes.length + "  " + Utils.bytesToHex(bytes));
        if (bytes.length > 0) {
            switch (step) {
                case C0:
                    step = Step.S0;
                    version = bytes[0];
                    outputStream.write(Utils.bytesArrayIntercept(bytes, 1, bytes.length - 1));
                    if (S_VERSION == version) {
                        context.channel().writeAndFlush(Unpooled.copiedBuffer(new byte[]{ S_VERSION }));

                    } else {
                        context.close();
                    }
                    return null;
                case S0:
                    outputStream.write(bytes);
                    if (outputStream.size() == CS1_LENGTH) {

                        final ByteBuf buf2 = Unpooled.buffer(CS1_LENGTH);
                        buf2.writeBytes(new byte[]{0, 0, 0, 0, 9, 0, 124, 2});
                        // 00 00 00 00 09 00 7C 02
                        buf2.writeBytes(R_BYTES);
                        System.out.println("发送握手的数据 @1 ...");
                        context.channel().writeAndFlush(buf2);

                        step = Step.S1;
                        final ByteBuf buf = Unpooled.buffer(CS1_LENGTH);
                        buf.writeBytes(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
                        buf.writeBytes(Utils.bytesArrayIntercept(outputStream.toByteArray(), 8, CS1_LENGTH - 8));
                        System.out.println("发送握手的数据 @2 ...");
                        context.channel().writeAndFlush(buf);
                    }
                    return null;
                case S1:
                    if (bytes.length >= CS2_LENGTH) {
                        step = Step.S2;
                        final byte[] bs = Utils.bytesArrayIntercept(bytes, 0, CS2_LENGTH);
                        final byte[] content = Utils.bytesArrayIntercept(bytes, CS2_LENGTH, bytes.length - CS2_LENGTH);
                        // bs 验证
                        System.out.println("验证完成 --> " + Arrays.toString(content));
                        return content;
                    } else {
                        throw new RuntimeException("[ 处理数据 ] 没有对应的步骤");
                    }
                default:
                    throw new RuntimeException("[ 处理数据 ] 没有对应的步骤");
            }
        } else {
            throw new RuntimeException("[ 处理数据 ] 没有对应的步骤");
        }
    }

    /**
     * 获取当前的步骤结果
     * @return 步骤结果
     */
    public Step getStep() {
        return step;
    }

    /**
     * 获取当前的时间
     * @return 时间
     */
    public int getTime() {
        return time;
    }

    /**
     * 获取当前的版本
     * @return 版本
     */
    public int getVersion() {
        return version;
    }

    public String getInputRandom() {
        return inputRandom;
    }

    public String getOutputRandom() {
        return outputRandom;
    }
}
