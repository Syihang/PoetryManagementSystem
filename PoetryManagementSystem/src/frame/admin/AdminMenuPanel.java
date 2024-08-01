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

        // 重绘主面板
        this.add(contentPanel, BorderLayout.CENTER);
        contentPanel.removeAll();
        contentPanel.repaint();


        JMenu inputMenu = new JMenu("诗词管理");
        menuBar.add(inputMenu);
        inputMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new PoetryManage());
            }
        });

        JMenu dialogMenu = new JMenu("用户管理");
        menuBar.add(dialogMenu);
        dialogMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new UserManage());
            }
        });

        JMenu comboMenu = new JMenu("添加管理员");
        menuBar.add(comboMenu);
        comboMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AddAdminDialog(getParentJFrame()).setVisible(true);
            }
        });

        JMenu checkMenu = new JMenu("个人信息");
        menuBar.add(checkMenu);
        checkMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setContent(new adminPersonalPanel());
            }
        });

        JMenu iconMenu = new JMenu("图片");
//        menuBar.add(iconMenu);
        iconMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                setContent(new IconPanel());
            }
        });

        JMenu layoutMenu = new JMenu("布局");
//        menuBar.add(layoutMenu);
        layoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                setContent(new LayoutPanel());
            }
        });

        JMenu tableMenu = new JMenu("表格");
//        menuBar.add(tableMenu);

        JMenuItem subMenu1 = new JMenuItem("普通表格");
        JMenuItem subMenu2 = new JMenuItem("滚动表格");
        JMenuItem subMenu3 = new JMenuItem("级联表格");
        JMenuItem subMenu4 = new JMenuItem("隐藏列");
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
