package frame.admin.Dialog;

import database.DataInsert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class AddPoetryDialog extends JDialog {
    public AddPoetryDialog(Frame owner) {
        super(owner, "添加诗词", true); // true 表示模态对话框
        initDialog();
    }

    JTextField titleField;
    JTextField authorField;
    JComboBox<String> dynastyComboBox;
    JComboBox<String> typeComboBox;
    JTextArea textArea;
    private void initDialog() {
        // 设置对话框的大小
        this.setSize(500, 400);
        // 获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 计算对话框应居中的位置
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        // 设置对话框的位置
        this.setLocation(x, y);

        JPanel south = new JPanel();
        JLabel southLabel = new JLabel("添加诗词");
        Font font = new Font("微软雅黑", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("宋体", Font.BOLD, 20);

        // 添加标题
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("标题:");
        titleLabel.setFont(boxFont);
        titleField = new JTextField(20);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(titleField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // 添加作者
        Box box2 = Box.createHorizontalBox();
        JLabel authorLabel = new JLabel("作者:");
        authorLabel.setFont(boxFont);
        authorField = new JTextField(20);
        box2.add(authorLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(authorField);
        box.add(box2);
        box.add(Box.createVerticalStrut(15));


        // 创建可编辑的下拉选择框
        String[] dynasties = {"唐代", "宋代", "元代", "现代"};
        dynastyComboBox = new JComboBox<>(dynasties);
        dynastyComboBox.setEditable(true);  // 允许用户自定义输入
        dynastyComboBox.setPreferredSize(new Dimension(200, dynastyComboBox.getPreferredSize().height));

        // 添加朝代
        Box box3 = Box.createHorizontalBox();
        JLabel dynastyLabel = new JLabel("朝代:");
        dynastyLabel.setFont(boxFont);
        box3.add(dynastyLabel);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(dynastyComboBox);
        box.add(box3);
        box.add(Box.createVerticalStrut(15));
        dynastyComboBox.setSelectedItem(null);

        // 创建可编辑的下拉选择框
        String[] types = {"唐诗", "宋词", "元曲", "现代诗", "先秦文学", "暂无归类"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setEditable(true);  // 允许用户自定义输入
        typeComboBox.setPreferredSize(new Dimension(200, typeComboBox.getPreferredSize().height));

        // 添加类别
        Box box4 = Box.createHorizontalBox();
        JLabel typeLabel = new JLabel("类型:");
        typeLabel.setFont(boxFont);
        box4.add(typeLabel);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(typeComboBox);
        box.add(box4);
        box.add(Box.createVerticalStrut(15));
        typeComboBox.setSelectedItem(null);

        // 创建文本域
        textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);  // 自动换行
        textArea.setWrapStyleWord(true);  // 换行不断字
        // 将文本区域放入滚动面板中
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // 添加正文
        Box box5 = Box.createHorizontalBox();
        JLabel textLabel = new JLabel("内容:");
        textLabel.setFont(boxFont);
        box5.add(textLabel);
        box5.add(Box.createHorizontalStrut(10));
        box5.add(scrollPane);
        box.add(box5);
        box.add(Box.createVerticalStrut(15));


        Box boxButton = Box.createHorizontalBox();
        JButton addBut = new JButton("添加");
        JButton clearBut = new JButton("清空");

        addBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPoetry();
            }
        });

        clearBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearText();
            }
        });

        boxButton.add(addBut);
        boxButton.add(Box.createHorizontalStrut(100));
        boxButton.add(clearBut);

        box.add(boxButton);

        jPanel.add(box, BorderLayout.CENTER);

        this.add(jPanel, BorderLayout.CENTER);

        // 设置对话框的默认关闭操作
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void addPoetry(){
        String title = titleField.getText();
        String author = authorField.getText();
        String dynasty = (String) dynastyComboBox.getSelectedItem();
        String type = (String) typeComboBox.getSelectedItem();
        String text = textArea.getText().trim();

        if (dynasty == null) {
            JOptionPane.showMessageDialog(this, "朝代不得为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (type == null) {
            JOptionPane.showMessageDialog(this, "类别不得为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入文本", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("标题: " + title);
        System.out.println("作者: " + author);
        System.out.println("朝代: " + dynasty);
        System.out.println("类别: " + type);
        System.out.println("文本: " + text);

        DataInsert.PoetryInsert(title, author, dynasty, type, text);

        JOptionPane.showMessageDialog(this, "添加成功", "提示", JOptionPane.WARNING_MESSAGE);
    }

    private void clearText(){
        titleField.setText("");
        authorField.setText("");
        dynastyComboBox.setSelectedIndex(-1);
        typeComboBox.setSelectedItem(null);
        textArea.setText("");
    }
}
