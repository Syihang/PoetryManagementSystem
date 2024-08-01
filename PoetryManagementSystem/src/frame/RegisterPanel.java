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

        // ������ֱ��
        Box box = Box.createVerticalBox();
        panel.add(box);
        box.add(Box.createVerticalStrut(15));

        Box box0 = Box.createHorizontalBox();
        JLabel title = new JLabel("�û�ע��");
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
        JLabel nameLabel = new JLabel("��        ��:");
        nameLabel.setFont(font);
        JTextField nameField = new JTextField(15);
        nameField.setBorder(border);
        box1.add(nameLabel);
        box1.add(nameField);
        box.add(box1);

        box.add(Box.createVerticalStrut(15));

        // ȷ����������
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("��        ��:");
        passwordLabel.setFont(font);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(border);
        box2.add(passwordLabel);
        box2.add(passwordField);
        box.add(box2);

        box.add(Box.createVerticalStrut(15));

        Box box3 = Box.createHorizontalBox();
        JLabel passwordLabel2 = new JLabel("ȷ������:");
        passwordLabel2.setFont(font);
        JPasswordField passwordField2 = new JPasswordField(15);
        passwordField2.setBorder(border);
        box3.add(passwordLabel2);
        box3.add(passwordField2);
        box.add(box3);

        box.add(Box.createVerticalStrut(15));

        // �ֻ���
        Box box4 = Box.createHorizontalBox();
        JLabel telLabel = new JLabel("��  ��  ��:");
        telLabel.setFont(font);
        JTextField telField = new JTextField(15);
        telField.setBorder(border);
        box4.add(telLabel);
        box4.add(telField);
        box.add(box4);

        box.add(Box.createVerticalStrut(15));

        Box box5 = Box.createHorizontalBox();

        JButton sureReg = new JButton("ȷ��ע��");
        JButton retuLog = new JButton("���ص�¼");
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
            JOptionPane.showMessageDialog(this, "�˺Ż����벻��Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else if (!password.equals(password2)) {
            JOptionPane.showMessageDialog(this, "�����������벻һ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else if (password.length() < 6){
            JOptionPane.showMessageDialog(this, "���볤�Ȳ���С��6���ַ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else {
            if (DataInsert.UserInsert(account, password, telephone)) {
                JOptionPane.showMessageDialog(this, "ע��ɹ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(this, "�û����ظ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    private void returnLogin() {
        // ��ȡ����������JFrame��
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // �л��������ΪLoginPanel
        topFrame.setContentPane(new LoginPanel());

        // ���²��ֶ�������
        topFrame.revalidate();
        topFrame.repaint();
    }
}
