package frame;

import util.SystemConstants;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setTitle("何当共剪西窗烛,却话巴山夜雨时");
        this.setSize(SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLocationRelativeTo(null); // 窗口居中
        this.setResizable(false); // 禁止调整窗口大小
        this.setContentPane(new LoginPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
