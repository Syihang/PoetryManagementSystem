package frame;

import util.SystemConstants;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setTitle("�ε�����������,ȴ����ɽҹ��ʱ");
        this.setSize(SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLocationRelativeTo(null); // ���ھ���
        this.setResizable(false); // ��ֹ�������ڴ�С
        this.setContentPane(new LoginPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
