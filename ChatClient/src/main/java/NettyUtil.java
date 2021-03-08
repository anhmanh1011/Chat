import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NettyUtil {
    public static ChannelFuture ctxWrite(ChannelHandlerContext ctx, String msg) {
        return ctx.writeAndFlush(encode(msg));
    }

    static byte[] encode(String s) {
        try {
            int length = s.getBytes(StandardCharsets.UTF_8).length;

            byte[] data = new byte[Constant.ST + Constant.PACKET_TYPE + Constant.BODY_LENGTH + length];
            ByteBuffer buf = ByteBuffer.wrap(data);


            buf.put(strToBytes(Constant.ST_STR));


            int type = 1;
            if (!s.contains(Constant.LOGIN)) {
                type = 2;
            }
            buf.put(intToByte(type));
            buf.put(intToByte(length));
            buf.put(s.getBytes(StandardCharsets.UTF_8));

            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    static String decode(byte[] array) throws Exception {
        int header = Constant.BODY_LENGTH + Constant.ST + Constant.PACKET_TYPE;
        byte[] bytes = Arrays.copyOfRange(array, Constant.ST + Constant.PACKET_TYPE, header);
        int num = ByteBuffer.wrap(bytes).getInt();

        byte[] byteData = Arrays.copyOfRange(array, header, header + num);
        return new String(byteData);
    }

    public static byte[] byteInit(int allocate, Object o) {
        byte[] add = new byte[allocate];

        if (o instanceof String) {
            String o1 = (String) o;
            add = ByteBuffer.wrap(add).put(o1.getBytes(StandardCharsets.UTF_8)).array();
        }

        if (o instanceof Number) {
            Number number = (Number) o;
            add = ByteBuffer.wrap(add).putInt((Integer) number).array();
        }
        return add;
    }

    public static byte[] bytePlus(byte[] b1, int allocate, Object o) {

        byte[] add = new byte[allocate];
        if (o instanceof String) {
            String o1 = (String) o;
            add = ByteBuffer.wrap(add).put(o1.getBytes(StandardCharsets.UTF_8)).array();
        }

        if (o instanceof Number) {
            Number number = (Number) o;
            add = ByteBuffer.wrap(add).putInt((Integer) number).array();
        }

        byte[] c = new byte[b1.length + add.length];
        System.arraycopy(b1, 0, c, 0, b1.length);
        System.arraycopy(add, 0, c, add.length, b1.length);
        return c;
    }



    public static byte[] intToByte(int myInteger) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).put((byte) myInteger).array();
    }

    public static byte[] strToBytes(String str) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).put(str.getBytes(StandardCharsets.UTF_8)).array();
    }

}
