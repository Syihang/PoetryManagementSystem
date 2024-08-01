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

        super("�û�����", true, true, true, true);

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
        String[] items = {"�û�ID", "�û���", "����", "�绰"};
        comboBox = new JComboBox<>(items);
        jTextField = new JTextField(8);
        selectBtu = new JButton("����");

        selectBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh();
            }
        });

        box.add(comboBox);
        box.add(jTextField);
        box.add(selectBtu);

        deleteBtu = new JButton("ɾ���û�");
        addBtu = new JButton("����û�");
        modifyBtu = new JButton("�޸��û�");
        checkBox = new JCheckBox("��ʾ����");

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

        // ���ñ���͸��
        checkBox.setOpaque(false);

        // ȡ��ѡ��ʱ�ĺ�ɫ�߿�
        checkBox.setBorderPainted(false);

        // �Զ���ѡ�к�δѡ��״̬����ɫ����ѡ��
        checkBox.setFocusPainted(false); // ȥ���������߿�
        checkBox.setContentAreaFilled(false); // ȥ���������
        checkBox.setBorder(null); // ȥ���߿�

        north.add(box);
        north.add(deleteBtu);
        north.add(addBtu);
        north.add(modifyBtu);
        north.add(checkBox);

        this.add(north, BorderLayout.NORTH);
    }

    // ����е�����
    Object[][] data = {};
    DefaultTableModel tableModel;
    JTable jTable;

    private void initCenter() {
        Object[] cloumnNames = {"ID", "�û���", "����", "��ϵ�绰", "��Ծ��"};

        tableModel = new DefaultTableModel(data, cloumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // ���е�Ԫ�񶼲��ɱ༭
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
        // ��ӱ߿���
        center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // ����ҳ�߾�
        center.setBorder(new EmptyBorder(0, 50, 50, 50));

        // ���ñ���������иߣ�
        jTable.setFont(new Font("Serif", Font.PLAIN, 18));
        jTable.setRowHeight(30);
        // �Զ����ͷ����ʽ��
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        tableHeader.setBackground(Color.GRAY);
        tableHeader.setForeground(Color.WHITE);
        // �Զ��嵥Ԫ����Ⱦ����
        jTable.setGridColor(Color.LIGHT_GRAY);
        // �Զ��嵥Ԫ����Ⱦ����
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // ����ѡ����ɫ
        jTable.setSelectionBackground(Color.CYAN);
        jTable.setSelectionForeground(Color.BLACK);


        this.add(center, BorderLayout.CENTER);
    }

    // ���������û�
    private void findAllUsers(){
        ArrayList<User> users = DataSelece.getAllUsers();
        tableModel.setRowCount(0);
        for (User user:users){
            String password = checkBox.isSelected()? user.getPassword():"******";
            Object[] rowData = {user.getId(), user.getName(), password, user.getTelephone(), user.getSatrs()};
            tableModel.addRow(rowData);
        }
    }
    // �����û�
    private void findUser(String item, String text){
        ArrayList<User> users = DataSelece.findUser(item, text);
        tableModel.setRowCount(0);
        for (User user:users){
            String password = checkBox.isSelected()? user.getPassword():"******";
            Object[] rowData = {user.getId(), user.getName(), password, user.getTelephone(), user.getSatrs()};
            tableModel.addRow(rowData);
        }
    }
    // ˢ�±��
    private void refresh(){
        if (jTextField.getText().isEmpty()){
            findAllUsers();
        } else {
            findUser(Objects.requireNonNull(comboBox.getSelectedItem()).toString(), jTextField.getText());
        }
    }
    // ɾ���û�
    private void DeleteUser() {
        int[] selectRows = jTable.getSelectedRows();

        if (selectRows.length == 0) {
            JOptionPane.showMessageDialog(this, "����ѡ������", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            //  ����ֵ, �Ƿ���0, �񷵻�1, ȡ������2, ֱ�ӹرշ���-1
            int res = JOptionPane.showConfirmDialog(this, "����ѡ��" + selectRows.length + "������,�Ƿ�ȷ��ɾ��", "����", JOptionPane.YES_NO_OPTION);
            if (res != 0) return;
        }
        for(int x:selectRows){
            int id = getSelectUserID(x); // ��ȡѡ���е�id
            DataDelete.UserDelete(id); // ɾ����Ϣ
        }
        refresh();
    }
    // ��ȡѡ�е��û�ID
    private int getSelectUserID(int x) {
        if (x < 0) return -1;

        // ��ȡ���ģ��
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // ��ȡid
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
    // ����û�
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
    // �����û�
    private void updateUser(){
        int[] selectRows = jTable.getSelectedRows();
        if (selectRows.length == 0) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�޸ĵ�����", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectRows.length > 1) {
            JOptionPane.showMessageDialog(this, "���ѡ��һ������", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = getSelectuserID(selectRows[0]); // ��ȡѡ���е�id
        new ModifyUserDialog(getParentJFrame(), id).setVisible(true);
        refresh();
    }
    // ��ȡѡ���е��û�ID
    private int getSelectuserID(int x) {
        if (x < 0) return -1;

        // ��ȡ���ģ��
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // ��ȡid
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("ID");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(x, columnIndexInModel);
    }
}
