package frame.admin;

import frame.admin.Dialog.AddAdminDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMenuPanel extends JPanel {
    private static JDesktopPane contentPanel = new JDesktopPane();

    public AdminMenuPanel() {
        JMenuBar menuBar = new JMenuBar();

        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);

        // �ػ������
        this.add(contentPanel, BorderLayout.CENTER);
        contentPanel.removeAll();
        contentPanel.repaint();


        JMenu inputMenu = new JMenu("ʫ�ʹ���");
        menuBar.add(inputMenu);
        inputMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new PoetryManage());
            }
        });

        JMenu dialogMenu = new JMenu("�û�����");
        menuBar.add(dialogMenu);
        dialogMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new UserManage());
            }
        });

        JMenu comboMenu = new JMenu("��ӹ���Ա");
        menuBar.add(comboMenu);
        comboMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AddAdminDialog(getParentJFrame()).setVisible(true);
            }
        });

        JMenu checkMenu = new JMenu("������Ϣ");
        menuBar.add(checkMenu);
        checkMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setContent(new adminPersonalPanel());
            }
        });

        JMenu iconMenu = new JMenu("ͼƬ");
//        menuBar.add(iconMenu);
        iconMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                setContent(new IconPanel());
            }
        });

        JMenu layoutMenu = new JMenu("����");
//        menuBar.add(layoutMenu);
        layoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                setContent(new LayoutPanel());
            }
        });

        JMenu tableMenu = new JMenu("���");
//        menuBar.add(tableMenu);

        JMenuItem subMenu1 = new JMenuItem("��ͨ���");
        JMenuItem subMenu2 = new JMenuItem("�������");
        JMenuItem subMenu3 = new JMenuItem("�������");
        JMenuItem subMenu4 = new JMenuItem("������");
        tableMenu.add(subMenu1);
        tableMenu.add(subMenu2);
        tableMenu.add(subMenu3);
        tableMenu.add(subMenu4);

        subMenu1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        subMenu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        subMenu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void setContent(JInternalFrame internalFrame){
        internalFrame.setSize(885, 540);
        internalFrame.setVisible(true);
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.add(internalFrame);
        contentPanel.revalidate();
    }

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
