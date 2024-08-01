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
        super(owner, "修改用户", true); // true 表示模态对话框
        initDialog(id);
    }

    JTextField userField;
    JTextField passwordField;
    JTextField telephoneField;

    private void initDialog(int id) {

        // 设置对话框的大小
        this.setSize(500, 400);

        // 获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 计算对话框应居中的位置
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        // 设置对话框的位置
        this.setLocation(x, y);

        JPanel south = new JPanel();
        JLabel southLabel = new JLabel("添加用户");
        Font font = new Font("微软雅黑", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("宋体", Font.BOLD, 20);

        // 添加标题
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("用户名:");
        titleLabel.setFont(boxFont);
        userField = new JTextField(20);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(userField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // 添加作者
        Box box2 = Box.createHorizontalBox();
        JLabel passLabel = new JLabel("密  码:");
        passLabel.setFont(boxFont);
        passwordField = new JTextField(20);
        box2.add(passLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(passwordField);
        box.add(box2);
        box.add(Box.createVerticalStrut(15));

        Box box3 = Box.createHorizontalBox();
        JLabel telLabel = new JLabel("手机号:");
        telLabel.setFont(boxFont);
        telephoneField = new JTextField(20);
        box3.add(telLabel);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(telephoneField);
        box.add(box3);
        box.add(Box.createVerticalStrut(15));

        Box boxButton = Box.createHorizontalBox();
        JButton addBut = new JButton("修改");
        JButton clearBut = new JButton("清空");

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

        // 设置对话框的默认关闭操作
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void updateUser(int id){
        String user = userField.getText().trim();
        String pass = passwordField.getText().trim();
        String tel = telephoneField.getText().trim();

        if (userField == null) {
            JOptionPane.showMessageDialog(this, "用户名不得为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (passwordField == null) {
            JOptionPane.showMessageDialog(this, "密码不得为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("标题: " + user);
        System.out.println("作者: " + pass);
        System.out.println("文本: " + tel);

        if (DataUpdate.UserUpdate(id,user, pass, tel)){
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "用户名重复", "提示", JOptionPane.WARNING_MESSAGE);
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
