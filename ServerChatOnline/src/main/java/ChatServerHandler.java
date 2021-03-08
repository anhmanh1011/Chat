import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    public static final String _SP = "/";

    Map<String, Channel> clientConnections = new HashMap<>();

    ChatHistoryDB chatHistoryDB = new ChatHistoryDB();

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (clientConnections.containsValue(ctx.channel())) {
            clientConnections.values().remove(ctx.channel());
        }
        super.handlerRemoved(ctx);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        System.out.println("Client " + ctx.channel().id().asLongText() + " Da ket noi ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = (String) msg;
        String[] split = data.split(_SP);
        String Command = split[0];
        if (Command.equals(Constant.LOGIN)) {
            Channel channel = clientConnections.get(split[1]);
            if (channel == null) {
                clientConnections.put(split[1], ctx.channel());
            }
            ctx.writeAndFlush(Constant.LOGIN + _SP + "done");
        }
        if (Command.equals(Constant.CHAT)) {

            String fromUser = split[1];
            String toUser = split[2];
            String content = split[3];

            ChatHistoryEntity chatHistoryEntity = new ChatHistoryEntity();
            chatHistoryEntity.setFromUser(fromUser);
            chatHistoryEntity.setToUser(toUser);
            chatHistoryEntity.setMessage(content);
            Calendar calendar = Calendar.getInstance();
            chatHistoryEntity.setCreateTime(new java.sql.Timestamp(calendar.getTime().getTime()));
            chatHistoryDB.save(chatHistoryEntity);
//
//            Channel channel = clientConnections.get(toUser);
//            String sendContent = Constant.LOAD + _SP + fromUser + _SP + toUser + _SP + content;
//
//            ctx.writeAndFlush(sendContent);
//            if (channel != null) {
//                channel.writeAndFlush(sendContent);
//            }

            sendMessage(ctx, fromUser, toUser);


        }
        if (Command.equals(Constant.LOAD)) {
            String fromUser = split[1];
            String toUser = split[2];
//
//            List<ChatHistoryEntity> all = chatHistoryDB.findAllByFromUserAndToUser(fromUser, toUser);
//
//            Channel channel = clientConnections.get(toUser);
//            all.forEach(chatHistoryEntity -> {
//                String sendMess = Constant.LOAD + _SP + chatHistoryEntity.getFromUser() + _SP + chatHistoryEntity.getToUser() + _SP + chatHistoryEntity.getMessage();
//                if (channel != null) {
//                    channel.writeAndFlush(sendMess);
//                }
//                ctx.writeAndFlush(sendMess);
//            });

            sendMessage(ctx, fromUser, toUser);

        }

    }


    public void sendMessage(ChannelHandlerContext ctx, String fromUser, String toUser) {
        List<ChatHistoryEntity> all = chatHistoryDB.findAllByFromUserAndToUser(fromUser, toUser);

        Channel channel = clientConnections.get(toUser);
        all.forEach(chatHistoryEntity -> {
            String sendMess = Constant.LOAD + _SP + chatHistoryEntity.getFromUser() + _SP + chatHistoryEntity.getToUser() + _SP + chatHistoryEntity.getMessage();
            if (channel != null) {
                channel.writeAndFlush(sendMess);
            }
            ctx.writeAndFlush(sendMess);
        });
    }

}
