import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.swing.*;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    Login login;
    FindYourFriend findYourFriend;
    ShowChat showChat;
    public static final String _SP = "/";

    public ChatClientHandler() {
    }

    public ChatClientHandler(Login login, FindYourFriend findYourFriend, ShowChat showChat) {
        this.login = login;
        this.findYourFriend = findYourFriend;
        this.showChat = showChat;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = (String) msg;
        String[] split = data.split(_SP);
        String command = split[0];
        if (command.equals(Constant.LOGIN)) {
            if (split[1].equals("done")) {
                login.setVisible(false);
                findYourFriend.setVisible(true);
            }else if(split[1].equals("false")) {
                JOptionPane.showMessageDialog(null, "User Da Duoc Dang Nhap o 1 noi khac !!! ");
            }

        } else if (command.equals(Constant.LOAD)) {
            findYourFriend.setVisible(false);
            showChat.setVisible(true);
            if (split.length > 2) {
                showChat.getTxtShowChat().append(split[1] + ":" + split[3] + "\n");
            }
        }

    }


    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public FindYourFriend getFindYourFriend() {
        return findYourFriend;
    }

    public void setFindYourFriend(FindYourFriend findYourFriend) {
        this.findYourFriend = findYourFriend;
    }

    public ShowChat getShowChat() {
        return showChat;
    }

    public void setShowChat(ShowChat showChat) {
        this.showChat = showChat;
    }
}
