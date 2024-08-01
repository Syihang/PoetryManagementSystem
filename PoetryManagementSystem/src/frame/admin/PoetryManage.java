package frame.admin;

import database.DataDelete;
import database.DataSelece;
import frame.admin.Dialog.AddPoetryDialog;
import frame.admin.Dialog.ModifyPoetryDialog;
import pojo.Poetry;
import util.SystemConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

public class PoetryManage extends JInternalFrame{
    public PoetryManage() {
        super("ʫ�ʹ���", true, true, true, true);
        initNorth();
        initCenter();
        initSouth();

        // Ĭ�����
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    // ����е�����
    Object[][] data = {};
    DefaultTableModel tableModel;
    JTable jTable;

    private void initNorth() {
        Font titleFont = new Font("����", Font.BOLD, 20);
        JLabel findPoetrys = new JLabel("����ʫ��");
        findPoetrys.setFont(titleFont);
        JButton clear = new JButton("���");
        JButton retrieveAll = new JButton("ȫ������");
        JButton addPoetry = new JButton("���ʫ��");
        JButton updatePoetry = new JButton("�޸�ʫ��");
        JButton deletePoetry = new JButton("ɾ��ʫ��");

        clear.addActionListener(e -> tableModel.setRowCount(0));

        retrieveAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshTable();
            }
        });
        JPanel north = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };

        addPoetry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddPoetry();
            }
        });

        updatePoetry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UpdatePoetry();
            }
        });

        deletePoetry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeletePoetry();
            }
        });


        north.add(findPoetrys, FlowLayout.LEFT);
        north.add(clear, FlowLayout.CENTER);
        north.add(deletePoetry, FlowLayout.RIGHT);
        north.add(updatePoetry, FlowLayout.RIGHT);
        north.add(addPoetry, FlowLayout.RIGHT);
        north.add(retrieveAll, FlowLayout.RIGHT);


        this.add(north, BorderLayout.NORTH);
    }

    private void initCenter() {
        // ��������
        Object[] cloumnNames = {"���", "����", "����", "���", "����", "�ղ�����"};

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
        center.setBorder(new EmptyBorder(0, 50, 0, 50));

        beautiful();

        JPanel centerPanel = new JPanel();
        centerPanel.add(center);

        this.add(center, BorderLayout.CENTER);

        refreshTable();
    }


    JPanel boxPanel;
    JScrollPane jScrollPane;
    private void initSouth() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new GridLayout(2,7,5,5));

        Font modeFont = new Font("����", Font.TYPE1_FONT, 18);
        JLabel id = new JLabel("���");
        JLabel name = new JLabel("����");
        JLabel birthday = new JLabel("����");
        JLabel sex = new JLabel("����");
        JLabel provience = new JLabel("���");
        JLabel home = new JLabel("����");

        id.setFont(modeFont);
        name.setFont(modeFont);
        birthday.setFont(modeFont);
        sex.setFont(modeFont);
        provience.setFont(modeFont);
        home.setFont(modeFont);
        // ��һ��
        panel.add(id);
        panel.add(name);
        panel.add(birthday);
        panel.add(sex);
        panel.add(provience);
        panel.add(home);
        panel.add(new JLabel());

        // �ڶ���
        JTextField jTextField1 = new JTextField();
        JTextField jTextField2 = new JTextField();
        JTextField jTextField3 = new JTextField();
        JTextField jTextField4 = new JTextField();
        JTextField jTextField5 = new JTextField();
        JTextField jTextField6 = new JTextField();

        panel.add(jTextField1);
        panel.add(jTextField2);
        panel.add(jTextField3);
        panel.add(jTextField4);
        panel.add(jTextField5);
        panel.add(jTextField6);

        JButton selectPoetry = new JButton("��ѯ");
        panel.add(selectPoetry);

        selectPoetry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                findPoetry(jTextField1.getText(), jTextField2.getText(), jTextField3.getText(),
                        jTextField4.getText(), jTextField5.getText(), jTextField6.getText());
            }
        });


        boxPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        boxPanel.setPreferredSize(new Dimension(600, 150));

        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshText();
            }
        });
//        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        jScrollPane = new JScrollPane(boxPanel);
        jScrollPane.setPreferredSize(new Dimension(620, 170));

        JPanel south = new JPanel();
        south.setLayout(new BorderLayout());

        south.add(panel, BorderLayout.NORTH);
        south.add(jScrollPane, BorderLayout.CENTER);

        // ����ҳ���
        panel.setBorder(new EmptyBorder(0, 50, 0, 50));

        this.add(south, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        ArrayList<Poetry> poetries = DataSelece.getAllPoetrys();
        tableModel.setRowCount(0);
        for (Poetry p:poetries) {
            String dynast = p.getDynasty() +" . " + p.getAuthor();
            int stars = DataSelece.getStarNumber(p.getPoetry_id());
            Object[] rowData = {p.getPoetry_id(), p.getTitle(), dynast, p.getType(), p.getText()[0], stars};
            tableModel.addRow(rowData);
        }
    }

    private int getSelectPoetrID() {
        int rowNum = jTable.getSelectedRow();
        if (rowNum < 0) return -1;

        // ��ȡ���ģ��
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // ��ȡid
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("���");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(rowNum, columnIndexInModel);
    }

    private int getSelectPoetrID(int x) {
        if (x < 0) return -1;

        // ��ȡ���ģ��
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // ��ȡid
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("���");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(x, columnIndexInModel);
    }

    Box box;
    private void refreshText() {
        int id = getSelectPoetrID();
        if (id < 0) return;

        Poetry poetry = DataSelece.IDtoPoetry(id);

        // �����������;
        boxPanel.removeAll();

        // ��������
        Font titleFont = new Font("����", Font.BOLD, 20); // Example: Arial, bold, size 18
        Font authorFont = new Font("����", Font.ITALIC, 14); // Example: Times New Roman, italic, size 14
        Font contentFont = new Font("����", Font.PLAIN, 15); // Example: Calibri, plain, size 12
        box = Box.createVerticalBox();
        assert poetry != null;
        // ��ӱ���
        JLabel jLabelTitle = new JLabel(poetry.getTitle());
        jLabelTitle.setFont(titleFont);
        box.add(jLabelTitle);
        box.add(Box.createVerticalStrut(20));
        // �������
        JLabel jLabelAuthor = new JLabel(poetry.getDynasty() + "��" + poetry.getAuthor());
        jLabelAuthor.setFont(authorFont);
        box.add(jLabelAuthor);
        box.add(Box.createVerticalStrut(20));
        // �������
        String[] text = poetry.getText();
        for (int i = 0 ; i < text.length; i = i + 2){
            String strtext = text[i];
            if (i  + 1 < text.length) strtext += text[i + 1];
            JLabel jLabelContent = new JLabel(strtext);
            jLabelContent.setFont(contentFont);
            box.add(jLabelContent);
            box.add(Box.createVerticalStrut(8));
        }
        boxPanel.add(box);
        // ���»������
        boxPanel.revalidate();
        boxPanel.repaint();
        jScrollPane.revalidate();
        jScrollPane.repaint();
    }
    // ����������ʫ��
    private void findPoetry(String newId, String newTitle, String newDynast, String newAuthor,
                            String newType, String newText){
        ArrayList<Poetry> poetries = DataSelece.FindByCriteria(newId, newTitle, newDynast, newAuthor, newType, newText);
        tableModel.setRowCount(0);
        for (Poetry p:poetries) {
            String dynast = p.getDynasty() +" . " + p.getAuthor();
            int stars = DataSelece.getStarNumber(p.getPoetry_id());
            Object[] rowData = {p.getPoetry_id(), p.getTitle(), dynast, p.getType(), p.getText()[0], stars};
            tableModel.addRow(rowData);
        }
    }
    // �����ʫ�����
    private void AddPoetry(){
        new AddPoetryDialog(getParentJFrame()).setVisible(true);
        refreshTable();
    }
    // ��ȡ��ǰ����������JFrame��
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
    // ɾ��ʫ��
    private void DeletePoetry(){
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
            int id = getSelectPoetrID(x); // ��ȡѡ���е�id
            DataDelete.PoetryDelete(id); // ɾ����Ϣ
        }
        refreshTable();
    }
    // ����ʫ��
    private void UpdatePoetry(){
        int[] selectRows = jTable.getSelectedRows();
        if (selectRows.length == 0) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�޸ĵ�����", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectRows.length > 1) {
            JOptionPane.showMessageDialog(this, "���ѡ��һ������", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = getSelectPoetrID(selectRows[0]); // ��ȡѡ���е�id
        new ModifyPoetryDialog(getParentJFrame(), id).setVisible(true);
        refreshTable();
    }
    // �������
    private void beautiful(){
        // ���ñ���������иߣ�
        jTable.setFont(new Font("Serif", Font.PLAIN, 16));
        jTable.setRowHeight(20);
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
    }

}
