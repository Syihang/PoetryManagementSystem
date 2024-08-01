package frame;

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

public class LoginPanel extends JPanel {

    private static JDesktopPane contentPanel = new JDesktopPane();
    String dir = "src\\images\\";
    JPanel panel;
    public LoginPanel() {
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

        // ������ֱ��
        Box box = Box.createVerticalBox();
        panel.add(box);
        box.add(Box.createVerticalStrut(15));

        Box box0 = Box.createHorizontalBox();
        JLabel title = new JLabel("ʫ�ʹ���ϵͳ");
        title.setFont( new Font("΢���ź�", Font.BOLD, 30));
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

        // �����������ı����ʽ
        Font font = new Font("΢���ź�", Font.BOLD, 20);
        Border border = BorderFactory.createCompoundBorder();

        // �˺�����
        Box box1 = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("��    ��:");
        nameLabel.setFont(font);
        JTextField nameField = new JTextField(15);
        nameField.setBorder(border);
        box1.add(nameLabel);
        box1.add(nameField);
        box.add(box1);

        box.add(Box.createVerticalStrut(15));

        // ��������
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("��    ��:");
        passwordLabel.setFont(font);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(border);
        box2.add(passwordLabel);
        box2.add(passwordField);
        box.add(box2);


        // Ȩ�ް�ť
        JRadioButton adminRadio = new JRadioButton("����Ա");
        JRadioButton commonRadio = new JRadioButton("��ͨ�û�", true);
        adminRadio.setFont(font);
        adminRadio.setOpaque(false);
        adminRadio.setFocusPainted(false);

        commonRadio.setFont(font);
        commonRadio.setOpaque(false);
        commonRadio.setFocusPainted(false);

        ButtonGroup group = new ButtonGroup();// ����һ����ť��
        group.add(adminRadio);
        group.add(commonRadio);
        Box box3 = Box.createHorizontalBox();
        box3.add(adminRadio);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(commonRadio);

        box.add(box3);
        box.add(Box.createVerticalStrut(15));

        JButton loginBtn = new JButton();
        Image login = new ImageIcon(dir + "login_btn.png").getImage().getScaledInstance(110, 60, Image.SCALE_DEFAULT);
        loginBtn.setIcon(new ImageIcon(login));
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorder(null);

        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginSkip(nameField.getText(), new String(passwordField.getPassword()), commonRadio.isSelected());
            }
        });


        JButton regBtn = new JButton();
        Image reg = new ImageIcon(dir + "reg_btn.png").getImage().getScaledInstance(110, 60, Image.SCALE_DEFAULT);
        regBtn.setIcon(new ImageIcon(reg));
        regBtn.setBorderPainted(false);
        regBtn.setOpaque(false);
        regBtn.setContentAreaFilled(false);
        regBtn.setBorder(null);

        regBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                returnRegister();
            }
        });

        Box box4 = Box.createHorizontalBox();
        box4.add(loginBtn);
        box4.add(Box.createHorizontalStrut(20));
        box4.add(regBtn);
        box.add(box4);

    }

    private void loginSkip(String name, String password, boolean isUser) {
        if (isUser) {
            judgeUser(name, password, true);
        }else {
            judgeUser(name, password, false);
        }
    }

    private void judgeUser(String name, String password, boolean isUser){
        if (name == null) {
            return;
        }
        if (DataSelece.getPassword(name, isUser) == null) {
            // ����,�˺Ų�����
            JOptionPane.showMessageDialog(this, "�û���������", "��ʾ", JOptionPane.WARNING_MESSAGE);

        } else if (!DataSelece.getPassword(name, isUser).equals(password)) {
            // �����������
            JOptionPane.showMessageDialog(this, "�������", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (isUser) {
                    new UserFrame().setVisible(true);
                } else {
                    new AdminFrame().setVisible(true);
                }
                // ��ȡ panel ���ڵ� JFrame ����
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                // ���ڿ���ʹ�� parentFrame ������в���������رմ���
                parentFrame.dispose();  // �ر� JFrame
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void returnRegister() {
        // ��ȡ����������JFrame��
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // �л��������ΪLoginPanel
        topFrame.setContentPane(new RegisterPanel());

        // ���²��ֶ�������
        topFrame.revalidate();
        topFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon(dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
    }

}
