package frame.user;

import database.DataSelece;
import database.DataUpdate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModifyUserPasswordDialog extends JDialog {
    public ModifyUserPasswordDialog(Frame owner, int id) {
        super(owner, "�޸��û�����", true); // true ��ʾģ̬�Ի���
        initDialog(id);
    }

    JPasswordField oldPasswordField;
    JPasswordField newPasswordField;
    JPasswordField newPasswordField2;

    private void initDialog(int id) {

        // ���öԻ���Ĵ�С
        this.setSize(500, 300);

        // ��ȡ��Ļ��С
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // ����Ի���Ӧ���е�λ��
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        // ���öԻ����λ��
        this.setLocation(x, y);

        JPanel south = new JPanel();
        JLabel southLabel = new JLabel("�޸�����");
        Font font = new Font("΢���ź�", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("����", Font.BOLD, 20);

        // ��ӱ���
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("�� �� ��:");
        titleLabel.setFont(boxFont);
        oldPasswordField = new JPasswordField(15);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(oldPasswordField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // �������
        Box box2 = Box.createHorizontalBox();
        JLabel passLabel = new JLabel("�� �� ��:");
        passLabel.setFont(boxFont);
        newPasswordField = new JPasswordField(15);
        box2.add(passLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(newPasswordField);
        box.add(box2);
        box.add(Box.createVerticalStrut(15));

        Box box3 = Box.createHorizontalBox();
        JLabel telLabel = new JLabel("ȷ������:");
        telLabel.setFont(boxFont);
        newPasswordField2 = new JPasswordField(15);
        box3.add(telLabel);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(newPasswordField2);
        box.add(box3);
        box.add(Box.createVerticalStrut(15));

        Box boxButton = Box.createHorizontalBox();
        JButton updateBut = new JButton("�޸�");
        JButton clearBut = new JButton("���");

        updateBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updatePassword(id);
            }
        });

        clearBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearText();
            }
        });

        boxButton.add(updateBut);
        boxButton.add(Box.createHorizontalStrut(100));
        boxButton.add(clearBut);

        box.add(boxButton);

        jPanel.add(box, BorderLayout.CENTER);

        this.add(jPanel, BorderLayout.CENTER);

        // ���öԻ����Ĭ�Ϲرղ���
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void updatePassword(int id){
        String old = new String(oldPasswordField.getPassword());
        String new1 = new String(newPasswordField.getPassword());
        String new2 = new String(newPasswordField2.getPassword());

        String oldSurePassword = DataSelece.getPasswordByID(id, true);
//        System.out.println(oldSurePassword);
        if (!old.equals(oldSurePassword)) {
            JOptionPane.showMessageDialog(this, "�������", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return ;
        }
        if (old.isEmpty()) {
            JOptionPane.showMessageDialog(this, "�û�������Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (new1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "���벻��Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!new1.equals(new2)){
            JOptionPane.showMessageDialog(this, "�����������벻һ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
            DataUpdate.updateUserPassword(id, new1);
            JOptionPane.showMessageDialog(this, "�޸ĳɹ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
    }

    private void clearText(){
        oldPasswordField.setText("");
        newPasswordField.setText("");
        newPasswordField2.setText("");
    }

}

