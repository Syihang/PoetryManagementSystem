package frame.admin;

import database.DataDelete;
import database.DataSelece;
import frame.admin.Dialog.AddUserDialog;
import frame.admin.Dialog.ModifyUserDialog;
import pojo.User;
import util.SystemConstants;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Objects;

public class UserManage extends JInternalFrame {

    public UserManage() {

        super("用户管理", true, true, true, true);

        setMaximizable(true);

        initNorth();
        initCenter();
//        initSouth();

        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    JComboBox<String> comboBox;
    JTextField jTextField;
    JButton selectBtu;

    JButton deleteBtu;
    JButton addBtu;
    JButton modifyBtu;
    JCheckBox checkBox;

    private void initNorth() {

        JPanel north = new JPanel(new FlowLayout()){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };

        Box box = Box.createHorizontalBox();
        String[] items = {"用户ID", "用户名", "密码", "电话"};
        comboBox = new JComboBox<>(items);
        jTextField = new JTextField(8);
        selectBtu = new JButton("查找");

        selectBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh();
            }
        });

        box.add(comboBox);
        box.add(jTextField);
        box.add(selectBtu);

        deleteBtu = new JButton("删除用户");
        addBtu = new JButton("添加用户");
        modifyBtu = new JButton("修改用户");
        checkBox = new JCheckBox("显示密码");

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        addBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddUser();
            }
        });

        deleteBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeleteUser();
            }
        });

        modifyBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateUser();
            }
        });

        // 设置背景透明
        checkBox.setOpaque(false);

        // 取消选择时的黑色边框
        checkBox.setBorderPainted(false);

        // 自定义选中和未选中状态的颜色（可选）
        checkBox.setFocusPainted(false); // 去掉焦点虚线框
        checkBox.setContentAreaFilled(false); // 去掉内容填充
        checkBox.setBorder(null); // 去掉边框

        north.add(box);
        north.add(deleteBtu);
        north.add(addBtu);
        north.add(modifyBtu);
        north.add(checkBox);

        this.add(north, BorderLayout.NORTH);
    }

    // 表格中的数据
    Object[][] data = {};
    DefaultTableModel tableModel;
    JTable jTable;

    private void initCenter() {
        Object[] cloumnNames = {"ID", "用户名", "密码", "联系电话", "活跃度"};

        tableModel = new DefaultTableModel(data, cloumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 所有单元格都不可编辑
                return false;
            }
        };
        jTable = new JTable(tableModel);
        JScrollPane center = new JScrollPane(jTable){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };

        jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // 添加边框线
        center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // 设置页边距
        center.setBorder(new EmptyBorder(0, 50, 50, 50));

        // 设置表格的字体和行高：
        jTable.setFont(new Font("Serif", Font.PLAIN, 18));
        jTable.setRowHeight(30);
        // 自定义表头的样式：
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        tableHeader.setBackground(Color.GRAY);
        tableHeader.setForeground(Color.WHITE);
        // 自定义单元格渲染器：
        jTable.setGridColor(Color.LIGHT_GRAY);
        // 自定义单元格渲染器：
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // 设置选中颜色
        jTable.setSelectionBackground(Color.CYAN);
        jTable.setSelectionForeground(Color.BLACK);


        this.add(center, BorderLayout.CENTER);
    }

    // 查找所有用户
    private void findAllUsers(){
        ArrayList<User> users = DataSelece.getAllUsers();
        tableModel.setRowCount(0);
        for (User user:users){
            String password = checkBox.isSelected()? user.getPassword():"******";
            Object[] rowData = {user.getId(), user.getName(), password, user.getTelephone(), user.getSatrs()};
            tableModel.addRow(rowData);
        }
    }
    // 查找用户
    private void findUser(String item, String text){
        ArrayList<User> users = DataSelece.findUser(item, text);
        tableModel.setRowCount(0);
        for (User user:users){
            String password = checkBox.isSelected()? user.getPassword():"******";
            Object[] rowData = {user.getId(), user.getName(), password, user.getTelephone(), user.getSatrs()};
            tableModel.addRow(rowData);
        }
    }
    // 刷新表格
    private void refresh(){
        if (jTextField.getText().isEmpty()){
            findAllUsers();
        } else {
            findUser(Objects.requireNonNull(comboBox.getSelectedItem()).toString(), jTextField.getText());
        }
    }
    // 删除用户
    private void DeleteUser() {
        int[] selectRows = jTable.getSelectedRows();

        if (selectRows.length == 0) {
            JOptionPane.showMessageDialog(this, "请先选中内容", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            //  返回值, 是返回0, 否返回1, 取消返回2, 直接关闭返回-1
            int res = JOptionPane.showConfirmDialog(this, "你已选择" + selectRows.length + "条数据,是否确认删除", "标题", JOptionPane.YES_NO_OPTION);
            if (res != 0) return;
        }
        for(int x:selectRows){
            int id = getSelectUserID(x); // 获取选中列的id
            DataDelete.UserDelete(id); // 删除信息
        }
        refresh();
    }
    // 获取选中的用户ID
    private int getSelectUserID(int x) {
        if (x < 0) return -1;

        // 获取表格模型
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // 获取id
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("ID");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(x, columnIndexInModel);
    }
    // debug
    public static void main(String[] args) {
        ArrayList<User> users = DataSelece.getAllUsers();

        for (User user:users){
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getPassword());
            System.out.println(user.getTelephone());
            System.out.println(user.getSatrs());
            System.out.println();
        }
    }
    // 添加用户
    private void AddUser(){
        new AddUserDialog(getParentJFrame()).setVisible(true);
        findAllUsers();
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
    // 更新用户
    private void updateUser(){
        int[] selectRows = jTable.getSelectedRows();
        if (selectRows.length == 0) {
            JOptionPane.showMessageDialog(this, "请选择要修改的内容", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectRows.length > 1) {
            JOptionPane.showMessageDialog(this, "最多选择一行数据", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = getSelectuserID(selectRows[0]); // 获取选中列的id
        new ModifyUserDialog(getParentJFrame(), id).setVisible(true);
        refresh();
    }
    // 获取选中列的用户ID
    private int getSelectuserID(int x) {
        if (x < 0) return -1;

        // 获取表格模型
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // 获取id
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("ID");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(x, columnIndexInModel);
    }
}
