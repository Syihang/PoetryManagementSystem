package frame.user;

import database.DataSelece;
import frame.MainFrame;
import frame.admin.Dialog.ModifyAdminPasswordDialog;
import pojo.Administrator;
import pojo.User;
import util.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class userPersonalPanel extends JInternalFrame {

    public userPersonalPanel() {
        super("�������", true, true, true, true);

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
        center.setLayout(new GridBagLayout()); // ʹ��GridBagLayout����

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "001.png").getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // ʹ��BoxLayout��ֱ����

//        Administrator admin = DataSelece.findByAdminID(SystemConstants.currentID);

        User user = DataSelece.findByUserID(SystemConstants.currentID);
        assert user != null;


        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("����", Font.BOLD, 32);
        Font valueFont = new Font("����", Font.BOLD, 32);

        JLabel title = new JLabel("������Ϣ");
        title.setFont(new Font("����", Font.BOLD, 45));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // ������ж���

        box.add(title);
        box.add(Box.createVerticalStrut(20));
        box.add(createBox("��  ��:", "��ͨ�û�", labelFont, valueFont));
        box.add(createBox("I    D:", String.valueOf(user.getId()), labelFont, valueFont));
        box.add(createBox("�û���:", user.getName(), labelFont, valueFont));
        box.add(createBox("��  ��:", user.getPassword(), labelFont, valueFont));
        box.add(createBox("��  ��:", user.getTelephone(), labelFont, valueFont));
        box.add(createBox("�ղ���:", "" + user.getSatrs(), labelFont, valueFont));
        box.add(Box.createVerticalStrut(20));
        Box boxBtu = Box.createHorizontalBox();
        JButton change = new JButton("�޸�����");
        JButton logOut = new JButton("�˳���¼");
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        change.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ModifyUserPasswordDialog(getParentJFrame(), SystemConstants.currentID).setVisible(true);

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
        box.add(Box.createHorizontalStrut(10)); // ���һ��ˮƽ���
        box.add(value);
        box.add(Box.createVerticalStrut(20)); // ���һ����ֱ���
        return box;
    }

    // �˳���¼
    private void logout() {
        SwingUtilities.getWindowAncestor(this).dispose(); // �رյ�ǰ����
        JFrame mainFrame = new MainFrame(); // ���� MainFrame ����
        mainFrame.setVisible(true); // ��ʾ MainFrame ����
    }

    // ��ȡ��JFrame
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
