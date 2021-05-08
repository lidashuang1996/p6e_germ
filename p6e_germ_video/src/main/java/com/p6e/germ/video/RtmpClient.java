package com.p6e.germ.video;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpClient {
    public static void main(String[] args) throws InterruptedException {
        final EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("正在连接中...");
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ChannelInboundHandler() {
                            @Override
                            public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelRegistered");


                            }

                            @Override
                            public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelUnregistered");
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelActive");

                                final String a = "030000000009007c02f778551eceab8e1e362f07c5868a70b266d40220e5086118a22e3697d5fc2b6329c22ee7c7c9b0b5b345dfa8398fea3bc879208a1068425aa1174cb57258cfebe24da42ec4ff9da19c7b406f18c95bfdd04ee48b6f833214a47b8bf68eb05254e25ae9a9a8b496c5496f6e9cd7c68cb6bcc84169e67f3e372ffe186da8513862c5afdeb5a1218a860baccdd869b253dbab8a0efb59a48c442099875e55b74fd07a1b69af303e099873cb77e2d9e2c4741eb1f506a7f0b2962af91f5051c890a320994f4c8b36a01fa9c0f15f4b7cf7b6c3817da60918611d3b02ff7cc94038937537d3382e416bd57577834df9caf748352150ce92d0dbb40e871fc364e18b7e3569e39876c1ae0cb79ff6d7ab32f9f011869c8da4191ef46d3c1bc6b72d83812f2243d1752a412632d66a2075f5d302f45cdf2b98b802b79805dc4837d84eded1cc42cba7eec3137bf86dd0b2d7f624d521a3ff655bcf0df4101341d230e94651628e45a7abf8183101ce981e5e86a6faec5c79aa7ec7181ea459ed966e0c932a6f9e4bf554ab7b1c905cde054ed40d2fb89bbfc65752e58cfde4f2a04dc3ad1e5ef4fc64d57b071c14146a451f6a043156d2880f5957d78dfd3ff2b880f624ac70f27094f37734eaf1924f66ac3d648ae232930544a4420b65eed4696f7f50fe05fc948516650bfb9b65f953d848e3456965d0d76b627a84e35e9ddabd8b43f0fadaee458cdf59ebd773922e0ae507d5e64092e90ec67ed8439e8f221c753a9f9216f1cb85b5107bc7cc7c4fb2c3eed1afc709b192f50de69c1ffca5650efb523b5fab5997cac4b7ef5af2bf5973e6293bae1fc1da499be5515077de4ddb3f7fbcd741711c62b7d803b8d054cf578182714d1c445bd692ba1b839a09e7266fc85daa8fad433dabbd206cde1ea571770c6bfa7956c266ee8435eb01caa6f583c0d83c33eefcf3f97db917bdb299f8a89fb8860ab1cd644c1e82cce1323a9eb79d25f5dc570b70b40bb20061743353d0536ecdabd82f2277756228cb54fe6932c4c9bd130d2bfc700bf5d85da6946e96f82e0afd8e9c20cfeabf9a6a189bb17bbef539d1d9e73ee966fcb5d6bdbf98444de865be1605e34087b34cdf04e3aff55bd4137c522d2e3c7bf2634db326b22a474db7f8a0952ab3510de555396c18a378a8390b73bff7772fb2760fa5786a3b0459c45a1d1b0dff4cbbb65da0ed34f32116c3c2cc2b95825111bcfbb246c1505aa00c970d06d76cc950ce368b220db20487cba02bb1fc54b36567b42913d6b885062cd7795601d31a4a8e6d56467fa5c52162676fd526122513c3e47f2e6d9d6a19c4f8795431a94ced039221d27ac68a7a770e9305252271459b71d05505c4977b70e96851208bacdcdf7e682b3848840880f1fec2a32fb1406862bc83cad89adf729f3ba6ff";
                                final String b = "d3fe808e8403e7c6a0f6dcda3211898f6f16505b078cc78b80db600ded2f200f25ff5dcb31d32cbe2056b8bdc31091631abbd2ac28d61fe32480ec5035bbf35f173393f0d59a21d2300b2db4b59eaa63ffe0f93d65dc028516905f22a9c7dff387c4eb78c6300e7ebbb2a6663cfbe5a9977caf5764ba3f0aba57db4e6051e0aec6e05b1bdaa649a97875edbc40bcc4214bb7fef72730d4368acbd3e10ab4c7642a1c53f93ca26db9c766555be26fdff0e2a721425b95762b0b776fd3e3eb17313d53129c9f34003c99a5196a8c6aacb6d64b51dd98b27c5b59a70bd941a1ba0300fb744cfc499d997e6d93392d28a767d116dfbc2cb9eb40540d11eb304ad9322386fdc597d55d3d1c67bc54df35479afb19ea2cd204dd331efb49c1e172f6bb89fbf58207dcae6c12f8a7598bef1e872c0c35f22b4c29df10f9bb1add3aeb887ab5fb38c036b88346f761c42f295dfe0b437dfb4f30a71276ab7d409ce424963f9ebb4f1f47e52b18786f279b5a87226f66981dd6d0ad11f49af31055ff69710fda3457c4b7852db2e3936d0778bf879f1734ad3a24ffb1ae9584760b9cb0452a53ba0962ffe2f605af52830bd211a5488894cc0b052255048711cd198510a9e943bf8b839198455fbd41073005d303990b88d9b63656d43cfec8ed83748f4b0f0fc5120216794b22a054e5bc58abd8c41096070884393453ce509694afbeabe0";

                                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(Utils.hexToByte(a)));
                                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(Utils.hexToByte(b)));
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelInactive");
                            }

                            int index = 0;

                            @Override
                            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                final String c = "0000000000000000f778551eceab8e1e362f07c5868a70b266d40220e5086118a22e3697d5fc2b6329c22ee7c7c9b0b5b345dfa8398fea3bc879208a1068425aa1174cb57258cfebe24da42ec4ff9da19c7b406f18c95bfdd04ee48b6f833214a47b8bf68eb05254e25ae9a9a8b496c5496f6e9cd7c68cb6bcc84169e67f3e372ffe186da8513862c5afdeb5a1218a860baccdd869b253dbab8a0efb59a48c442099875e55b74fd07a1b69af303e099873cb77e2d9e2c4741eb1f506a7f0b2962af91f5051c890a320994f4c8b36a01fa9c0f15f4b7cf7b6c3817da60918611d3b02ff7cc94038937537d3382e416bd57577834df9caf748352150ce92d0dbb40e871fc364e18b7e3569e39876c1ae0cb79ff6d7ab32f9f011869c8da4191ef46d3c1bc6b72d83812f2243d1752a412632d66a2075f5d302f45cdf2b98b802b79805dc4837d84eded1cc42cba7eec3137bf86dd0b2d7f624d521a3ff655bcf0df4101341d230e94651628e45a7abf8183101ce981e5e86a6faec5c79aa7ec7181ea459ed966e0c932a6f9e4bf554ab7b1c905cde054ed40d2fb89bbfc65752e58cfde4f2a04dc3ad1e5ef4fc64d57b071c14146a451f6a043156d2880f5957d78dfd3ff2b880f624ac70f27094f37734eaf1924f66ac3d648ae232930544a4420b65eed4696f7f50fe05fc948516650bfb9b65f953d848e3456965d0d76b627a84e35e9ddabd8b43f0fadaee458cdf59ebd773922e0ae507d5e64092e90ec67ed8439e8f221c753a9f9216f1cb85b5107bc7cc7c4fb2c3eed1afc709b192f50de69c1ffca5650efb523b5fab5997cac4b7ef5af2bf5973e6293bae1fc1da499be5515077de4ddb3f7fbcd741711c62b7d803b8d054cf578182714d1c445bd692ba1b839a09e7266fc85daa8fad433dabbd206cde1ea571770c6bfa7956c266ee8435eb01caa6f583c0d83c33eefcf3f97db917bdb299f8a89fb8860ab1cd644c1e82cce1323a9eb79d25f5dc570b70b40bb20061743353d0536ecdabd82f2277756228cb54fe6932c4c9bd130d2bfc700bf5d85da6946e96f82e0afd8e9c20cfeabf9a6a189bb17bbef539d1d9e73ee966fcb5d6bdbf98444de865be1605e34087b34cdf04e3aff55bd4137c522d2e3c7bf2634db326b22a474db7f8a0952ab3510de555396c18a378a8390b73bff7772fb2760fa5786a3b0459c45a1d1b0dff4cbbb65da0ed34f32116c3c2cc2b95825111bcfbb246c1505aa00c970d06d76cc950ce368b220db20487cba02bb1fc54b36567b42913d6b885062cd7795601d31a4a8e6d56467fa5c52162676fd526122513c3e47f2e6d9d6a19c4f8795431a94ced039221d27ac68a7a770e9305252271459b71d05505c4977b70e96851208bacdcdf7e682b3848840880f1fec2a32fb1406862bc83cad89adf729f3ba6ffd3fe808e8403e7c6a0f6dcda3211898f6f16505b078cc78b80db600ded2f200f25ff5dcb31d32cbe2056b8bdc31091631abbd2ac28d61fe32480ec5035bbf35f173393f0d59a21d2300b2db4b59eaa63ffe0f93d65dc028516905f22a9c7dff387c4eb78c6300e7ebbb2a6663cfbe5a9977caf5764ba3f0aba57db4e6051e0aec6e05b1bdaa649a97875edbc40bcc4214bb7fef72730d4368acbd3e10ab4c7642a1c53f93ca26db9c766555be26fdff0e2a721425b95762b0b776fd3e3eb17313d53129c9f34003c99a5196a8c6aacb6d64b51dd98b27c5b59a70bd941a1ba0300fb744cfc499d997e6d93392d28a767d116dfbc2cb9eb40540d11eb304ad9322386fdc597d55d3d1c67bc54df35479afb19ea2cd204dd331efb49c1e172f6bb89fbf58207dcae6c12f8a7598bef1e872c0c35f22b4c29df10f9bb1add3aeb887ab5fb38c036b88346f761c42f295dfe0b437dfb4f30a71276ab7d409ce424963f9ebb4f1f47e52b18786f279b5a87226f66981dd6d0ad11f49af31055ff69710fda3457c4b7852db2e3936d0778bf879f1734ad3a24ffb1ae9584760b9cb0452a53ba0962ffe2f605af52830bd211a5488894cc0b052255048711cd198510a9e943bf8b839198455fbd41073005d303990b88d9b63656d43cfec8ed83748f4b0f0fc5120216794b22a054e5bc58abd8c41096070884393453ce509694afbeabe00300000000008c1400000000020007636f6e6e656374003ff00000000000000300036170700200046c69766500047479706502000a6e6f6e707269766174650008666c617368566572020024464d4c452f332e302028636f6d70617469626c653b204c61766635382e34352e313030290005746355726c02001b72746d703a2f2f3132372e302e302e313a31c3393030302f6c697665000009";
                                if (o instanceof ByteBuf) {
                                    ByteBuf buf = (ByteBuf) o;
                                    final byte[] bytes = new byte[buf.readableBytes()];
                                    buf.readBytes(bytes);
                                    System.out.println("ccc " + bytes.length + " ==> " + Arrays.toString(bytes));
                                    if (index == 1) {
                                         channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(Utils.hexToByte(c)));
                                    } else {
//                                        System.out.println(new RtmpDecoder().decodeBytesToMessage(bytes));
//                                        System.out.println(Utils.bytesToHex(bytes));
                                    }

                                    index ++;
                                }
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelReadComplete");
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                System.out.println("userEventTriggered");
                            }

                            @Override
                            public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("channelWritabilityChanged");
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                                System.out.println("exceptionCaught");
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("handlerAdded");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
                                System.out.println("handlerRemoved");
                            }
                        });

                    }
                });
         final ChannelFuture future = b.connect("127.0.0.1", 19000).sync();

    }
}
