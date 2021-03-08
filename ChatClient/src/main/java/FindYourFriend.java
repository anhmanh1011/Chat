import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindYourFriend extends JFrame {
    private JButton CHATButton;
    private JTextField txtFriend;
    private JPanel JPControl;
    private JPanel jpaneFriend;
    private JPanel jPBtn;
    public FindYourFriend(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(jpaneFriend);
        this.pack();
        centreWindow(this);
    }


    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public JButton getCHATButton() {
        return CHATButton;
    }

    public void setCHATButton(JButton CHATButton) {
        this.CHATButton = CHATButton;
    }

    public JTextField getTxtFriend() {
        return txtFriend;
    }

    public void setTxtFriend(JTextField txtFriend) {
        this.txtFriend = txtFriend;
    }

    public JPanel getJPControl() {
        return JPControl;
    }

    public void setJPControl(JPanel JPControl) {
        this.JPControl = JPControl;
    }

    public JPanel getJpaneFriend() {
        return jpaneFriend;
    }

    public void setJpaneFriend(JPanel jpaneFriend) {
        this.jpaneFriend = jpaneFriend;
    }

    public JPanel getjPBtn() {
        return jPBtn;
    }

    public void setjPBtn(JPanel jPBtn) {
        this.jPBtn = jPBtn;
    }
}
