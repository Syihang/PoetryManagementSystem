package frame.user;

import database.DataDelete;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyStar extends JInternalFrame{
    public MyStar() {
        super("我的收藏", true, true, true, true);
        initNorth();
        initCenter();
        initSouth();

        // 默认最大化
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    // 表格中的数据
    Object[][] data = {};
    DefaultTableModel tableModel;
    JTable jTable;

    private void initNorth() {
        Font titleFont = new Font("宋体", Font.BOLD, 20);
        JLabel studentTitle = new JLabel("收藏管理");
        studentTitle.setFont(titleFont);
        JButton clear = new JButton("清空");
        JButton retrieveAll = new JButton("全部检索");
        JButton unfavorite = new JButton("取消收藏");
        JButton exportBtu = new JButton("导出");

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

        unfavorite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Unfavorite();
            }
        });

        exportBtu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExportToFile(jTextField1.getText(), jTextField2.getText(), jTextField3.getText(),
                        jTextField4.getText(), jTextField5.getText(), jTextField6.getText());
            }
        });

        north.add(studentTitle, FlowLayout.LEFT);
        north.add(clear, FlowLayout.CENTER);
        north.add(exportBtu, FlowLayout.RIGHT);
        north.add(unfavorite, FlowLayout.RIGHT);
        north.add(retrieveAll, FlowLayout.RIGHT);


        this.add(north, BorderLayout.NORTH);
    }

    private void initCenter() {
        // 表格标题行
        Object[] cloumnNames = {"编号", "标题", "作者", "类别", "内容", "收藏人数"};

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
        center.setBorder(new EmptyBorder(0, 50, 0, 50));


        beautiful();

        JPanel centerPanel = new JPanel();
        centerPanel.add(center);

        this.add(center, BorderLayout.CENTER);

        refreshTable();
    }


    JPanel boxPanel;
    JScrollPane jScrollPane;

    // 多条件查询输入框
    JTextField jTextField1;
    JTextField jTextField2;
    JTextField jTextField3;
    JTextField jTextField4;
    JTextField jTextField5;
    JTextField jTextField6;

    private void initSouth() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new GridLayout(2,7,5,5));

        Font modeFont = new Font("楷体", Font.TYPE1_FONT, 18);
        JLabel id = new JLabel("编号");
        JLabel name = new JLabel("标题");
        JLabel birthday = new JLabel("朝代");
        JLabel sex = new JLabel("作者");
        JLabel provience = new JLabel("类别");
        JLabel home = new JLabel("内容");

        id.setFont(modeFont);
        name.setFont(modeFont);
        birthday.setFont(modeFont);
        sex.setFont(modeFont);
        provience.setFont(modeFont);
        home.setFont(modeFont);
        // 第一行
        panel.add(id);
        panel.add(name);
        panel.add(birthday);
        panel.add(sex);
        panel.add(provience);
        panel.add(home);
        panel.add(new JLabel());

        // 第二行
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();
        jTextField4 = new JTextField();
        jTextField5 = new JTextField();
        jTextField6 = new JTextField();

        panel.add(jTextField1);
        panel.add(jTextField2);
        panel.add(jTextField3);
        panel.add(jTextField4);
        panel.add(jTextField5);
        panel.add(jTextField6);

        JButton selectPoetry = new JButton("查询");
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

        // 设置页面距
        panel.setBorder(new EmptyBorder(0, 50, 0, 50));

        this.add(south, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        ArrayList<Poetry> poetries = DataSelece.getAllStarPoetry(SystemConstants.currentID);
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

        // 获取表格模型
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // 获取id
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("编号");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(rowNum, columnIndexInModel);
    }

    // 获取选中单元格的ID
    private int getSelectPoetrID(int x) {
        int rowNum = x;
        if (rowNum < 0) return -1;

        // 获取表格模型
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        // 获取id
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("编号");
        int columnIndexInModel = jTable.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(rowNum, columnIndexInModel);
    }

    Box box;
    private void refreshText() {
        int id = getSelectPoetrID();
        if (id < 0) return;

        Poetry poetry = DataSelece.IDtoPoetry(id);

        // 清空所有内容;
        boxPanel.removeAll();

        // 定义字体
        Font titleFont = new Font("宋体", Font.BOLD, 20); // Example: Arial, bold, size 18
        Font authorFont = new Font("楷体", Font.ITALIC, 14); // Example: Times New Roman, italic, size 14
        Font contentFont = new Font("宋体", Font.PLAIN, 15); // Example: Calibri, plain, size 12

        box = Box.createVerticalBox();
        assert poetry != null;
        // 添加标题
        JLabel jLabelTitle = new JLabel(poetry.getTitle());
        jLabelTitle.setFont(titleFont);
        box.add(jLabelTitle);
        box.add(Box.createVerticalStrut(20));
        // 添加作者
        JLabel jLabelAuthor = new JLabel(poetry.getDynasty() + "·" + poetry.getAuthor());
        jLabelAuthor.setFont(authorFont);
        box.add(jLabelAuthor);
        box.add(Box.createVerticalStrut(20));
        // 添加正文
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
        // 重新绘制面板
        boxPanel.revalidate();
        boxPanel.repaint();
        jScrollPane.revalidate();
        jScrollPane.repaint();
    }

    // 查找诗词
    private void findPoetry(String newId, String newTitle, String newDynast, String newAuthor,
                              String newType, String newText){
        ArrayList<Poetry> poetries = DataSelece.FindByStarCriteria(newId, newTitle, newDynast, newAuthor, newType, newText);
        tableModel.setRowCount(0);
        for (Poetry p:poetries) {
            String dynast = p.getDynasty() +" . " + p.getAuthor();
            int stars = DataSelece.getStarNumber(p.getPoetry_id());
            Object[] rowData = {p.getPoetry_id(), p.getTitle(), dynast, p.getType(), p.getText()[0], stars};
            tableModel.addRow(rowData);
        }
    }
    // 取消收藏
    private void Unfavorite(){
        int[] selectRows = jTable.getSelectedRows();

        if (selectRows.length == 0) return;
        for(int x:selectRows){
            int id = getSelectPoetrID(x); // 获取选中列的id
            DataDelete.StarDelete(id); // 取消收藏
        }

        for (int i = selectRows.length - 1; i >= 0; i--){
            tableModel.removeRow(selectRows[i]);
        }
    }

    // 美化表格
    private void beautiful(){
        // 设置表格的字体和行高：
        jTable.setFont(new Font("Serif", Font.PLAIN, 16));
        jTable.setRowHeight(20);
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
    }

    // 批量导出
    private void ExportToFile(String newId, String newTitle, String newDynast, String newAuthor,
                             String newType, String newText) {

        ArrayList<Poetry> poetries = DataSelece.FindByStarCriteria(newId, newTitle, newDynast, newAuthor, newType, newText);
        if (poetries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "未选择任何数据!", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 创建文件选择器
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择保存路径");

        // 显示保存对话框
        int userSelection = fileChooser.showSaveDialog(null);

        // 执行导出操作
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // 获取选择的文件
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // 如果文件没有扩展名，添加".txt"
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Poetry p : poetries) {
                    String dynast = p.getDynasty() + " . " + p.getAuthor();
                    int stars = DataSelece.getStarNumber(p.getPoetry_id());
                    String text = String.join("\n", p.getText()); // assuming p.getText() returns a String array
                    writer.write("ID: " + p.getPoetry_id());
                    writer.newLine();
                    writer.write("Title: " + p.getTitle());
                    writer.newLine();
                    writer.write("Dynasty and Author: " + dynast);
                    writer.newLine();
                    writer.write("Type: " + p.getType());
                    writer.newLine();
                    writer.write("Text: \n" + text);
                    writer.newLine();
                    writer.write("Stars: " + stars);
                    writer.newLine();
                    writer.write("----------------------------");
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "已成功保存" + poetries.size() + "条数据", "提示", JOptionPane.WARNING_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
