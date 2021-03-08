import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ChatEncoder extends MessageToByteEncoder<String> {
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
////        super.write(ctx, msg, promise);
//        super.write(ctx, encode((String) msg), promise);
//
//    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        ByteBuffer bufferST = ByteBuffer.allocate(Constant.ST).put(Constant.ST_STR.getBytes(StandardCharsets.UTF_8));
        ByteBuffer bufferPacketType = ByteBuffer.allocate(Constant.PACKET_TYPE);
        int length = s.getBytes(StandardCharsets.UTF_8).length;

        byte[] byteData = NettyUtil.byteInit(Constant.ST, Constant.ST_STR);

        int type = 1;
        if (!s.contains(Constant.LOGIN)) {
            type =2;
        }
        byteData = NettyUtil.bytePlus(byteData, Constant.PACKET_TYPE, type);
        byteData = NettyUtil.bytePlus(byteData,Constant.BODY_LENGTH, length);
        byteData = NettyUtil.bytePlus(byteData,length, s.getBytes(StandardCharsets.UTF_8));

        byteBuf.writeBytes(byteData);
    }

//    ByteBuffer encode(String s) throws Exception {
//        ByteBuffer bufferST = ByteBufferConverter.byteToByteBuffer("ST".getBytes(StandardCharsets.UTF_8), Constant.ST);
//        byte[] packetType = new byte[1];
//        packetType[0] = 1;
//        if (!s.contains(Constant.LOGIN)) {
//            packetType[0] = 2;
//        }
//        ByteBuffer bufferPacketType = ByteBufferConverter.byteToByteBuffer(packetType, Constant.PACKET_TYPE);
//        ByteBuffer bufferBodyLength = ByteBufferConverter.byteToByteBuffer(s.getBytes(StandardCharsets.UTF_8), Constant.BODY_LENGTH);
//        ByteBuffer bufferBody = ByteBufferConverter.byteToByteBuffer(s.getBytes(StandardCharsets.UTF_8), s.getBytes(StandardCharsets.UTF_8).length);
//        ByteBuffer put = bufferST.put(bufferPacketType).put(bufferBodyLength).put(bufferBody);
//        return put;
//    }
}
