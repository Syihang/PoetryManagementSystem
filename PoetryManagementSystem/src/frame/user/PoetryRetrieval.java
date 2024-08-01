package frame.user;

import database.DataDelete;
import database.DataInsert;
import database.DataSelece;
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

public class PoetryRetrieval extends JInternalFrame{
    public PoetryRetrieval() {
        super("ʫ�ʼ���", true, true, true, true);
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
        JLabel studentTitle = new JLabel("����ʫ��");
        studentTitle.setFont(titleFont);
        JButton clear = new JButton("���");
        JButton retrieveAll = new JButton("ȫ������");
        JButton unfavorite = new JButton("ȡ���ղ�");
        JButton addStar = new JButton("����ղ�");

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

        addStar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddStar();
            }
        });

        unfavorite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Unfavorite();
            }
        });

        north.add(studentTitle, FlowLayout.LEFT);
        north.add(clear, FlowLayout.CENTER);
        north.add(unfavorite, FlowLayout.RIGHT);
        north.add(addStar, FlowLayout.RIGHT);
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

        beautiful2();

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

    // ����ȫ�����ݲ����µ����
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

    // ��ȡѡ�б����ʫ�ʵ�ID
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
        int rowNum = x;
        if (rowNum < 0) return -1;

        // ��ȡ���ģ��
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // ��ȡid
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("���");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(rowNum, columnIndexInModel);
    }

    // �������ѡ�е�ʫ�ʸ������·����
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

    // ������ģ������
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

    // ȡ���ղ�
    private void Unfavorite(){
        int[] selectRows = jTable.getSelectedRows();

        if (selectRows.length == 0) return;
        for(int x:selectRows){
            int id = getSelectPoetrID(x); // ��ȡѡ���е�id
            DataDelete.StarDelete(id); // ȡ���ղ�
        }
        refreshTable();
    }

    private void AddStar(){
        int[] selectRows = jTable.getSelectedRows();

        if (selectRows.length == 0) return;
        for(int x:selectRows){
            int id = getSelectPoetrID(x); // ��ȡѡ���е�id
            DataInsert.StarInsert(id); // ȡ���ղ�
        }
        refreshTable();
    }
 // �������
    private void beautiful() {

        // ���ñ���������и�
        jTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        jTable.setRowHeight(20);

        // �Զ����ͷ����ʽ
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        tableHeader.setBackground(new Color(240, 240, 240));  // ǳ��ɫ
        tableHeader.setForeground(new Color(60, 60, 60));     // ���ɫ

        // ���ñ�����������ɫ
        jTable.setGridColor(new Color(200, 200, 200));  // ��ɫ

        // �Զ��嵥Ԫ����Ⱦ��
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : new Color(240, 240, 240)); // ��������ɫ����ɫ��ǳ��ɫ
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ����ѡ����ɫ
        jTable.setSelectionBackground(new Color(180, 220, 240));  // ǳ��ɫ
        jTable.setSelectionForeground(new Color(60, 60, 60));     // ���ɫ
    }

    private void beautiful2() {

        // ���ñ���������и�
        jTable.setFont(new Font("����", Font.PLAIN, 16));
        jTable.setRowHeight(25);

        // �Զ����ͷ����ʽ
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("����", Font.BOLD, 18));
        tableHeader.setBackground(new Color(139, 69, 19));  // ��ɫ
        tableHeader.setForeground(Color.WHITE);

        // ���ñ�����������ɫ
        jTable.setGridColor(new Color(160, 82, 45));  // ����ɫ

        // �Զ��嵥Ԫ����Ⱦ��
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 222, 179) : new Color(255, 235, 205)); // ��������ɫ��С��ɫ�����ʰ�
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ����ѡ����ɫ
        jTable.setSelectionBackground(new Color(210, 180, 140));  // ǳ��ɫ
        jTable.setSelectionForeground(Color.BLACK);
    }
}
