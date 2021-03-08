import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowChat extends JFrame {
    private JTextArea txtShowChat;
    private JTextField txtContent;
    private JButton btnSend;
    private JPanel JPMain;
    private Channel channel;
    private JTextField txtUserName;


    public ShowChat(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(JPMain);
        this.pack();
        centreWindow(this);

    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public JTextArea getTxtShowChat() {
        return txtShowChat;
    }

    public void setTxtShowChat(JTextArea txtShowChat) {
        this.txtShowChat = txtShowChat;
    }

    public JTextField getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(JTextField txtContent) {
        this.txtContent = txtContent;
    }

    public JButton getBtnSend() {
        return btnSend;
    }

    public void setBtnSend(JButton btnSend) {
        this.btnSend = btnSend;
    }

    public JPanel getJPMain() {
        return JPMain;
    }

    public void setJPMain(JPanel JPMain) {
        this.JPMain = JPMain;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }
}
