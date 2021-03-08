import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static final String _SP = "/";

    public static void main(String[] args) {


        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Login login = new Login("Login");
        FindYourFriend findYourFriend = new FindYourFriend("Chat with");
        ShowChat showChat = new ShowChat("Chat");

        SecureChatClientInitializer secureChatClientInitializer = new SecureChatClientInitializer(login, findYourFriend, showChat);
//
//        secureChatClientInitializer.getChatClientHandler().setShowChat(showChat);
//        secureChatClientInitializer.getChatClientHandler().setLogin(login);
//        secureChatClientInitializer.getChatClientHandler().setFindYourFriend(findYourFriend);
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(secureChatClientInitializer);
            ChannelFuture sync = b.connect(host, port).sync();
            Channel channel = sync.channel();
//            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            //User input


            findYourFriend.getCHATButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    channel.writeAndFlush(Constant.LOAD + _SP + login.getTxtUserName().getText() + _SP + findYourFriend.getTxtFriend().getText());
//                            .addListener(new ChannelFutureListener() {
//                        @Override
//                        public void operationComplete(ChannelFuture future) {
//                            if (future.isDone()) {
//                                showChat.setVisible(true);
//                            } else { // an error occurs, do perhaps something else
//                            }
//                        }
//                    });
                }
            });

            showChat.getBtnSend().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    channel.writeAndFlush(Constant.CHAT + _SP + login.getTxtUserName().getText() + _SP + findYourFriend.getTxtFriend().getText() + _SP + showChat.getTxtContent().getText());
                    showChat.getTxtContent().setText("");
                    showChat.getTxtShowChat().setText("");
                }
            });

            login.getBtnLogin().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    channel.writeAndFlush(Constant.LOGIN + _SP + login.getTxtUserName().getText());
//                            .addListener(new ChannelFutureListener() {
//                                                                                                                   @Override
//                                                                                                                   public void operationComplete(ChannelFuture future) {
//                                                                                                                       if (future.isDone()) {
//                                                                                                                           JOptionPane.showMessageDialog(null, "Dang nhap thanh cong");
//                                                                                                                           findYourFriend.setVisible(true);
//                                                                                                                       } else {
//                                                                                                                           JOptionPane.showMessageDialog(null, "Dang nhap that bai");
//                                                                                                                       }
//                                                                                                                   }
//                                                                                                               }
//                    );
                }
            });

            login.setVisible(true);


            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
            // Wait until the connection is closed.

        } finally {
            workerGroup.shutdownGracefully();
        }


    }
}
