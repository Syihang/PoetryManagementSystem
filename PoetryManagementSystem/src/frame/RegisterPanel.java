package frame;

import database.DataInsert;
import database.DataSelece;
import frame.admin.AdminFrame;
import frame.user.UserFrame;
import util.SystemConstants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class RegisterPanel extends JPanel {

    private static JDesktopPane contentPanel = new JDesktopPane();

    String dir = "src\\images\\";

    JPanel panel;

    public RegisterPanel() {
        this.setBounds(0, 0, SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLayout(null);

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(dir + "001.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        panel.setBounds(200, 100, 500, 300);
        this.add(panel);

        // 创建垂直表单
        Box box = Box.createVerticalBox();
        panel.add(box);
        box.add(Box.createVerticalStrut(15));

        Box box0 = Box.createHorizontalBox();
        JLabel title = new JLabel("用户注册");
        title.setFont( new Font("微软雅黑", Font.BOLD, 30));
        box0.add(title);
        box.add(box0);
        box.add(Box.createVerticalStrut(20));
        box0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                box0.setBackground(Color.LIGHT_GRAY);
                box0.repaint();
            }
        });

        // 设置字体与文本框格式
        Font font = new Font("微软雅黑", Font.BOLD, 20);
        Border border = BorderFactory.createCompoundBorder();

        // 账号输入
        Box box1 = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("账        号:");
        nameLabel.setFont(font);
        JTextField nameField = new JTextField(15);
        nameField.setBorder(border);
        box1.add(nameLabel);
        box1.add(nameField);
        box.add(box1);

        box.add(Box.createVerticalStrut(15));

        // 确认密码输入
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("密        码:");
        passwordLabel.setFont(font);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(border);
        box2.add(passwordLabel);
        box2.add(passwordField);
        box.add(box2);

        box.add(Box.createVerticalStrut(15));

        Box box3 = Box.createHorizontalBox();
        JLabel passwordLabel2 = new JLabel("确认密码:");
        passwordLabel2.setFont(font);
        JPasswordField passwordField2 = new JPasswordField(15);
        passwordField2.setBorder(border);
        box3.add(passwordLabel2);
        box3.add(passwordField2);
        box.add(box3);

        box.add(Box.createVerticalStrut(15));

        // 手机号
        Box box4 = Box.createHorizontalBox();
        JLabel telLabel = new JLabel("手  机  号:");
        telLabel.setFont(font);
        JTextField telField = new JTextField(15);
        telField.setBorder(border);
        box4.add(telLabel);
        box4.add(telField);
        box.add(box4);

        box.add(Box.createVerticalStrut(15));

        Box box5 = Box.createHorizontalBox();

        JButton sureReg = new JButton("确认注册");
        JButton retuLog = new JButton("返回登录");
        box5.add(sureReg);
        box5.add(Box.createHorizontalStrut(30));
        box5.add(retuLog);
        box.add(box5);

        sureReg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register(nameField.getText(), new String(passwordField.getPassword()),
                        new String(passwordField2.getPassword()) , telField.getText());
            }
        });

        retuLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                returnLogin();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon(dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
    }


    private void Register(String account, String password, String password2, String telephone){
        if (account.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "账号或密码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (!password.equals(password2)) {
            JOptionPane.showMessageDialog(this, "两次输入密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (password.length() < 6){
            JOptionPane.showMessageDialog(this, "密码长度不得小于6个字符", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            if (DataInsert.UserInsert(account, password, telephone)) {
                JOptionPane.showMessageDialog(this, "注册成功", "提示", JOptionPane.WARNING_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(this, "用户名重复", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    private void returnLogin() {
        // 获取顶层容器（JFrame）
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // 切换内容面板为LoginPanel
        topFrame.setContentPane(new LoginPanel());

        // 重新布局顶层容器
        topFrame.revalidate();
        topFrame.repaint();
    }
}
