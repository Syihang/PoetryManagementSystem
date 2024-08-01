package frame.admin;

import database.DataSelece;
import frame.MainFrame;
import frame.admin.Dialog.ModifyAdminPasswordDialog;
import pojo.Administrator;
import util.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class adminPersonalPanel extends JInternalFrame {

    public adminPersonalPanel() {
        super("个人面板", true, true, true, true);
        initCenter();
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void initCenter() {
        JPanel center = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        center.setLayout(new GridBagLayout()); // 使用GridBagLayout居中

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "001.png").getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 使用BoxLayout垂直排列

        Administrator admin = DataSelece.findByAdminID(SystemConstants.currentID);
        assert admin != null;

        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("宋体", Font.BOLD, 32);
        Font valueFont = new Font("宋体", Font.BOLD, 32);

        JLabel title = new JLabel("个人信息");
        title.setFont(new Font("宋体", Font.BOLD, 45));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 标题居中对齐

        box.add(title);
        box.add(Box.createVerticalStrut(20));
        box.add(createBox("身  份:", "管理员", labelFont, valueFont));
        box.add(createBox("I    D:", String.valueOf(admin.getId()), labelFont, valueFont));
        box.add(createBox("用户名:", admin.getAccount(), labelFont, valueFont));
        box.add(createBox("密  码:", admin.getPassword(), labelFont, valueFont));
        box.add(createBox("名  称:", admin.getName(), labelFont, valueFont));
        box.add(createBox("电  话:", admin.getTelephone(), labelFont, valueFont));
        box.add(Box.createVerticalStrut(20));
        Box boxBtu = Box.createHorizontalBox();
        JButton change = new JButton("修改密码");
        JButton logOut = new JButton("退出登录");

        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        change.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ModifyAdminPasswordDialog(getParentJFrame(), SystemConstants.currentID).setVisible(true);

            }
        });

        boxBtu.add(change);
        boxBtu.add(Box.createHorizontalStrut(60));
        boxBtu.add(logOut);

        box.add(boxBtu);
        panel.add(box);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        center.add(panel, gbc);

        this.add(center);
    }

    private Box createBox(String labelText, String valueText, Font labelFont, Font valueFont) {
        Box box = Box.createHorizontalBox();
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        JLabel value = new JLabel(valueText);
        value.setFont(valueFont);
        value.setForeground(Color.BLUE);
        box.add(label);
        box.add(Box.createHorizontalStrut(10)); // 添加一个水平间隔
        box.add(value);
        box.add(Box.createVerticalStrut(20)); // 添加一个垂直间隔
        return box;
    }

    // 退出登录
    private void logout() {
        SwingUtilities.getWindowAncestor(this).dispose(); // 关闭当前窗口
        JFrame mainFrame = new MainFrame(); // 创建 MainFrame 界面
        mainFrame.setVisible(true); // 显示 MainFrame 界面
    }
    // 获取当前页面所从属的JFrame面板
    private JFrame getParentJFrame() {
        Container parent = this.getParent();
        while (parent != null) {
            if (parent instanceof JFrame) {
                return (JFrame) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
}
