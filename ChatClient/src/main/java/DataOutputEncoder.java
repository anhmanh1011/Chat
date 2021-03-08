import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class DataOutputEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
        int dataLength = msg.getBytes(StandardCharsets.UTF_8).length;
        // Write a message.

        out.writeCharSequence("S",StandardCharsets.UTF_8);
        out.writeCharSequence("T",StandardCharsets.UTF_8);
        if(msg.contains(Constant.LOGIN)){
            out.writeShort(1);
        }else
            out.writeShort(2);
        out.writeShort(dataLength);  // data length
        out.writeBytes(msg.getBytes(StandardCharsets.UTF_8));      // data
    }
}
