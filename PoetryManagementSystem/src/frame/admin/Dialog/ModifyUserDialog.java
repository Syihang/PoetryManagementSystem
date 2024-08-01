package frame.admin.Dialog;

import database.DataInsert;
import database.DataSelece;
import database.DataUpdate;
import pojo.User;
import util.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModifyUserDialog extends JDialog {
    public ModifyUserDialog(Frame owner, int id) {
        super(owner, "�޸��û�", true); // true ��ʾģ̬�Ի���
        initDialog(id);
    }

    JTextField userField;
    JTextField passwordField;
    JTextField telephoneField;

    private void initDialog(int id) {

        // ���öԻ���Ĵ�С
        this.setSize(500, 400);

        // ��ȡ��Ļ��С
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // ����Ի���Ӧ���е�λ��
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        // ���öԻ����λ��
        this.setLocation(x, y);

        JPanel south = new JPanel();
        JLabel southLabel = new JLabel("����û�");
        Font font = new Font("΢���ź�", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("����", Font.BOLD, 20);

        // ��ӱ���
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("�û���:");
        titleLabel.setFont(boxFont);
        userField = new JTextField(20);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(userField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // �������
        Box box2 = Box.createHorizontalBox();
        JLabel passLabel = new JLabel("��  ��:");
        passLabel.setFont(boxFont);
        passwordField = new JTextField(20);
        box2.add(passLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(passwordField);
        box.add(box2);
        box.add(Box.createVerticalStrut(15));

        Box box3 = Box.createHorizontalBox();
        JLabel telLabel = new JLabel("�ֻ���:");
        telLabel.setFont(boxFont);
        telephoneField = new JTextField(20);
        box3.add(telLabel);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(telephoneField);
        box.add(box3);
        box.add(Box.createVerticalStrut(15));

        Box boxButton = Box.createHorizontalBox();
        JButton addBut = new JButton("�޸�");
        JButton clearBut = new JButton("���");

        addBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateUser(id);
            }
        });

        clearBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearText();
            }
        });

        boxButton.add(addBut);
        boxButton.add(Box.createHorizontalStrut(100));
        boxButton.add(clearBut);

        box.add(boxButton);
        setUserAllField(id);
        jPanel.add(box, BorderLayout.CENTER);

        this.add(jPanel, BorderLayout.CENTER);

        // ���öԻ����Ĭ�Ϲرղ���
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void updateUser(int id){
        String user = userField.getText().trim();
        String pass = passwordField.getText().trim();
        String tel = telephoneField.getText().trim();

        if (userField == null) {
            JOptionPane.showMessageDialog(this, "�û�������Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (passwordField == null) {
            JOptionPane.showMessageDialog(this, "���벻��Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("����: " + user);
        System.out.println("����: " + pass);
        System.out.println("�ı�: " + tel);

        if (DataUpdate.UserUpdate(id,user, pass, tel)){
            JOptionPane.showMessageDialog(this, "�޸ĳɹ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "�û����ظ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearText(){
        userField.setText("");
        passwordField.setText("");
        telephoneField.setText("");
    }

    private void setUserAllField(int id){
        User user = DataSelece.findByUserID(id);

        assert user != null;
        userField.setText(user.getName());
        passwordField.setText(user.getPassword());
        telephoneField.setText(user.getTelephone());
    }
}
