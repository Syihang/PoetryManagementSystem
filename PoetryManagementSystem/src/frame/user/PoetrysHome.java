package frame.user;

import com.mysql.cj.xdevapi.Table;
import database.DataDelete;
import database.DataInsert;
import database.DataSelece;
import pojo.Poetry;
import util.SystemConstants;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PoetrysHome extends JInternalFrame {

    public PoetrysHome() throws SQLException {
        super("诗词", true, true, true, true);
        initLeftPanel();
        initRightPanel();

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

    JTable table = new JTable();

    public void initLeftPanel() throws SQLException {
        JPanel LeftPanel = new JPanel();
        // 单选
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Object[] columnNames = {"题目", "作者", "正文", "编号"};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 所有单元格都不可编辑
                return false;
            }
        };

        table = new JTable(tableModel);

        ArrayList<Poetry> poetries = DataSelece.getAllPoetrys();

        for (Poetry poetry:poetries) {
            String title = poetry.getTitle();
            String author = poetry.getAuthor();
            String text = poetry.getText()[0];
            int id = poetry.getPoetry_id();
            Object[] rowData = {title, author, text, id};
            tableModel.addRow(rowData);
//            System.out.println(title);  // debug
        }
        JScrollPane jScrollPane = new JScrollPane(table);

        beautiful();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    refreshPanel();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        LeftPanel.add(jScrollPane, BorderLayout.CENTER);

        this.add(LeftPanel, BorderLayout.CENTER);

    }
    JPanel boxPanel = new JPanel(){
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
        }
    };

    JButton collectButtom;
    public void initRightPanel() {
        JPanel RightPanel = new JPanel();
        RightPanel.setLayout(new BorderLayout());

        JPanel PoetryPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "001.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        PoetryPanel.setPreferredSize(new Dimension(250, 450)); // 设置固定宽度，高度随内容增加
        PoetryPanel.setBackground(Color.GREEN); // 可以为了示例性而设置背景色，便于观察

        JScrollPane jScrollPane = new JScrollPane(boxPanel){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "bg1.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };
        jScrollPane.setPreferredSize(new Dimension(280, 460));

        RightPanel.add(jScrollPane, BorderLayout.CENTER);

        JPanel collectPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(SystemConstants.dir + "collect.png").getImage(), 0, 0,getWidth(), getHeight(), null);
            }
        };

        // 收藏按钮
        collectButtom = new JButton();

        collectButtom.setBorderPainted(false);
        collectButtom.setOpaque(false);
        collectButtom.setContentAreaFilled(false);
        collectButtom.setBorder(null);
        collectButtom.setPreferredSize(new Dimension(290, 50));
        collectPanel.add(collectButtom, BorderLayout.SOUTH);
        collectButtom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    collectPoetry();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        collectPanel.setPreferredSize(new Dimension(290, 50));

        RightPanel.add(collectPanel, BorderLayout.SOUTH);

        this.add(RightPanel, BorderLayout.EAST);
    }

    private boolean isCollect(int poetryID) throws SQLException {
        return DataSelece.isExitCollect(poetryID);
    }

    private void collectPoetry() throws SQLException {
        int poetryID = getSelectPoetrID();
        if (isCollect(poetryID)) {
            DataDelete.StarDelete(poetryID);
        } else {
            DataInsert.StarInsert(poetryID);
        }

        refreshStar(isCollect(poetryID));
    }

    private void refreshStar(boolean isStar) {
        if (isStar) {
            Image collect = new ImageIcon(SystemConstants.dir + "collected.png").getImage().getScaledInstance(300, 70, Image.SCALE_DEFAULT);
            collectButtom.setIcon(new ImageIcon(collect));
        }
        else {
            Image collect = new ImageIcon(SystemConstants.dir + "collect.png").getImage().getScaledInstance(300, 70, Image.SCALE_DEFAULT);
            collectButtom.setIcon(new ImageIcon(collect));
        }
    }

    private int getSelectPoetrID() {
        int rowNum = table.getSelectedRow();
        if (rowNum < 0) return -1;

        // 获取表格模型
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // 获取id
        TableColumnModel columnModel = table.getColumnModel();
        int columnIndexInView = columnModel.getColumnIndex("编号");
        int columnIndexInModel = table.convertColumnIndexToModel(columnIndexInView);

        return (int) model.getValueAt(rowNum, columnIndexInModel);
    }

    private void refreshPanel() throws SQLException {

        int id = getSelectPoetrID();

        if (id < 0) return;

        Poetry poetry = DataSelece.IDtoPoetry(id);

        // 更新右侧面板的内容
        boxPanel.removeAll(); // 清空原有内容

        // 定义字体
        Font titleFont = new Font("宋体", Font.BOLD, 20); // Example: Arial, bold, size 18
        Font authorFont = new Font("楷体", Font.ITALIC, 14); // Example: Times New Roman, italic, size 14
        Font contentFont = new Font("宋体", Font.PLAIN, 15); // Example: Calibri, plain, size 12

        Box box = Box.createVerticalBox();
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
        for (String content:poetry.getText()){
            JLabel jLabelContent = new JLabel(content);
            jLabelContent.setFont(contentFont);
            box.add(jLabelContent);
            box.add(Box.createVerticalStrut(8));
        }

        boxPanel.add(box);

        // 更新收藏按钮
        refreshStar(isCollect(id));
        // 重新绘制面板
        boxPanel.revalidate();
        boxPanel.repaint();

    }

    private void beautiful() {
        JTable jTable = table;

        // 设置表格的字体和行高
        jTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        jTable.setRowHeight(20);

        // 自定义表头的样式
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        tableHeader.setBackground(new Color(240, 240, 240));  // 浅灰色
        tableHeader.setForeground(new Color(60, 60, 60));     // 深灰色

        // 设置表格的网格线颜色
        jTable.setGridColor(new Color(200, 200, 200));  // 灰色

        // 自定义单元格渲染器
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : new Color(240, 240, 240)); // 交替行颜色：白色和浅灰色
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 设置选中颜色
        jTable.setSelectionBackground(new Color(255, 196, 148));  // 浅蓝色
        jTable.setSelectionForeground(new Color(60, 60, 60));     // 深灰色
    }

    private void beautiful2() {
        JTable jTable = table;

        // 设置表格的字体和行高
        jTable.setFont(new Font("楷体", Font.PLAIN, 16));
        jTable.setRowHeight(25);

        // 自定义表头的样式
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("黑体", Font.BOLD, 18));
        tableHeader.setBackground(new Color(139, 69, 19));  // 棕色
        tableHeader.setForeground(Color.WHITE);

        // 设置表格的网格线颜色
        jTable.setGridColor(new Color(160, 82, 45));  // 深棕色

        // 自定义单元格渲染器
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 222, 179) : new Color(255, 235, 205)); // 交替行颜色：小麦色和杏仁白
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 设置选中颜色
        jTable.setSelectionBackground(new Color(210, 180, 140));  // 浅棕色
        jTable.setSelectionForeground(Color.BLACK);
    }


}
