package frame.admin.Dialog;

import database.DataInsert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddUserDialog extends JDialog {
    public AddUserDialog(Frame owner) {
        super(owner, "�����û�", true); // true ��ʾģ̬�Ի���
        initDialog();
    }

    JTextField userField;
    JTextField passwordField;
    JTextField telephoneField;
//    JTextArea textArea;

    private void initDialog() {

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
        JLabel southLabel = new JLabel("�����û�");
        Font font = new Font("΢���ź�", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("����", Font.BOLD, 20);

        // ���ӱ���
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("�û���:");
        titleLabel.setFont(boxFont);
        userField = new JTextField(20);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(userField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // ��������
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
        JButton addBut = new JButton("����");
        JButton clearBut = new JButton("���");

        addBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPoetry();
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

        jPanel.add(box, BorderLayout.CENTER);

        this.add(jPanel, BorderLayout.CENTER);

        // ���öԻ����Ĭ�Ϲرղ���
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void addPoetry(){
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

        if (DataInsert.UserInsert(user, pass, tel)){
            JOptionPane.showMessageDialog(this, "���ӳɹ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "�û����ظ�", "��ʾ", JOptionPane.WARNING_MESSAGE);

        }


    }

    private void clearText(){
        userField.setText("");
        passwordField.setText("");
        telephoneField.setText("");
    }
}