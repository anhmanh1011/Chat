import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

//public class ChatDecoder extends ByteToMessageDecoder {
public class ChatDecoder extends ReplayingDecoder<DecoderState> {

    private int length;

    public ChatDecoder() {
        super(DecoderState.READ_CONTENT);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        System.out.println(buf.readableBytes());
        switch (state()) {
            case READ_LENGTH:
                length = buf.readInt();
                System.out.println("length is: " + length);
                checkpoint(DecoderState.READ_CONTENT);
            case READ_CONTENT:
                ByteBuf frame = buf.readBytes(length);
                checkpoint(DecoderState.READ_LENGTH);
                out.add(frame);
                break;
            default:
                throw new Error("Shouldn't reach here");
        }
    }
//
//    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//        byte[] array = byteBuf.array();
//        int length = array.length;
//        System.out.println(" okkk ");
//
//    }

//    //    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {


//        if (in.hasArray()) {
//            int header = Constant.BODY_LENGTH + Constant.ST + Constant.PACKET_TYPE;
//            int readableBytes = in.readableBytes();
//            while (readableBytes < header) {
//
//            }
//            byte[] array = in.array();
//            byte[] bytes = Arrays.copyOfRange(array, Constant.ST + Constant.PACKET_TYPE, header);
//            int num = ByteBuffer.wrap(bytes).getInt();
//            while (readableBytes < header + num) {
//
//            }
////        if (in.readableBytes() < header + num) return;
//            byte[] byteData = Arrays.copyOfRange(array, header, header + num);
//            String msg = new String(byteData);
//            out.add(msg);
//        }


//    }


//    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//        out.add(msg.toString(this.charset));
//    }


}
