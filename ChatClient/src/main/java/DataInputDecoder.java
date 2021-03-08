import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataInputDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < 6) {
            return;
        }
        in.markReaderIndex();
        byte s = in.readByte();
        byte t = in.readByte();
        if (s != (byte)'S' ) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid data: ");
        }
        if (t != (byte)'T' ) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid data: ");
        }

        short packetType = in.readShort();
        if (!(packetType == 1 || packetType == 2)) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid data: ");
        }

        short bodyLength = in.readShort();
        if (bodyLength < 1) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid data: ");
        }


        if (in.readableBytes() < bodyLength) {
            in.resetReaderIndex();
            return;
        }
        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[bodyLength];
        in.readBytes(decoded);
//        String decode = NettyUtil.decode(decoded);
        out.add(new String(decoded));
    }
}
