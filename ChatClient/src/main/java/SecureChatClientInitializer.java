import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {
    ChatClientHandler chatClientHandler ;

    public SecureChatClientInitializer(Login login, FindYourFriend findYourFriend, ShowChat showChat) {
        chatClientHandler =  new ChatClientHandler(login,findYourFriend,showChat);
    }



    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DataInputDecoder());
        pipeline.addLast(new DataOutputEncoder());
        pipeline.addLast(chatClientHandler);

    }

    public ChatClientHandler getChatClientHandler() {
        return chatClientHandler;
    }

    public void setChatClientHandler(ChatClientHandler chatClientHandler) {
        this.chatClientHandler = chatClientHandler;
    }
}
