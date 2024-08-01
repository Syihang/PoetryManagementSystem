package frame.admin.Dialog;

import database.DataInsert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class AddPoetryDialog extends JDialog {
    public AddPoetryDialog(Frame owner) {
        super(owner, "���ʫ��", true); // true ��ʾģ̬�Ի���
        initDialog();
    }

    JTextField titleField;
    JTextField authorField;
    JComboBox<String> dynastyComboBox;
    JComboBox<String> typeComboBox;
    JTextArea textArea;
    private void initDialog() {
        // ���öԻ���Ĵ�С
        this.setSize(500, 400);
        // ��ȡ��Ļ��С
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // ����Ի���Ӧ���е�λ��
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        // ���öԻ����λ��
        this.setLocation(x, y);

        JPanel south = new JPanel();
        JLabel southLabel = new JLabel("���ʫ��");
        Font font = new Font("΢���ź�", Font.BOLD, 25);
        southLabel.setFont(font);
        south.add(southLabel);
        this.add(south, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        Box box = Box.createVerticalBox();
        Font boxFont = new Font("����", Font.BOLD, 20);

        // ��ӱ���
        Box box1 = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("����:");
        titleLabel.setFont(boxFont);
        titleField = new JTextField(20);
        box1.add(titleLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(titleField);
        box.add(box1);
        box.add(Box.createVerticalStrut(15));

        // �������
        Box box2 = Box.createHorizontalBox();
        JLabel authorLabel = new JLabel("����:");
        authorLabel.setFont(boxFont);
        authorField = new JTextField(20);
        box2.add(authorLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(authorField);
        box.add(box2);
        box.add(Box.createVerticalStrut(15));


        // �����ɱ༭������ѡ���
        String[] dynasties = {"�ƴ�", "�δ�", "Ԫ��", "�ִ�"};
        dynastyComboBox = new JComboBox<>(dynasties);
        dynastyComboBox.setEditable(true);  // �����û��Զ�������
        dynastyComboBox.setPreferredSize(new Dimension(200, dynastyComboBox.getPreferredSize().height));

        // ��ӳ���
        Box box3 = Box.createHorizontalBox();
        JLabel dynastyLabel = new JLabel("����:");
        dynastyLabel.setFont(boxFont);
        box3.add(dynastyLabel);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(dynastyComboBox);
        box.add(box3);
        box.add(Box.createVerticalStrut(15));
        dynastyComboBox.setSelectedItem(null);

        // �����ɱ༭������ѡ���
        String[] types = {"��ʫ", "�δ�", "Ԫ��", "�ִ�ʫ", "������ѧ", "���޹���"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setEditable(true);  // �����û��Զ�������
        typeComboBox.setPreferredSize(new Dimension(200, typeComboBox.getPreferredSize().height));

        // ������
        Box box4 = Box.createHorizontalBox();
        JLabel typeLabel = new JLabel("����:");
        typeLabel.setFont(boxFont);
        box4.add(typeLabel);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(typeComboBox);
        box.add(box4);
        box.add(Box.createVerticalStrut(15));
        typeComboBox.setSelectedItem(null);

        // �����ı���
        textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);  // �Զ�����
        textArea.setWrapStyleWord(true);  // ���в�����
        // ���ı����������������
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // �������
        Box box5 = Box.createHorizontalBox();
        JLabel textLabel = new JLabel("����:");
        textLabel.setFont(boxFont);
        box5.add(textLabel);
        box5.add(Box.createHorizontalStrut(10));
        box5.add(scrollPane);
        box.add(box5);
        box.add(Box.createVerticalStrut(15));


        Box boxButton = Box.createHorizontalBox();
        JButton addBut = new JButton("���");
        JButton clearBut = new JButton("���");

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

        // ���öԻ����Ĭ�Ϲرղ���
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void addPoetry(){
        String title = titleField.getText();
        String author = authorField.getText();
        String dynasty = (String) dynastyComboBox.getSelectedItem();
        String type = (String) typeComboBox.getSelectedItem();
        String text = textArea.getText().trim();

        if (dynasty == null) {
            JOptionPane.showMessageDialog(this, "��������Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (type == null) {
            JOptionPane.showMessageDialog(this, "��𲻵�Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "�������ı�", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("����: " + title);
        System.out.println("����: " + author);
        System.out.println("����: " + dynasty);
        System.out.println("���: " + type);
        System.out.println("�ı�: " + text);

        DataInsert.PoetryInsert(title, author, dynasty, type, text);

        JOptionPane.showMessageDialog(this, "��ӳɹ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
    }

    private void clearText(){
        titleField.setText("");
        authorField.setText("");
        dynastyComboBox.setSelectedIndex(-1);
        typeComboBox.setSelectedItem(null);
        textArea.setText("");
    }
}
