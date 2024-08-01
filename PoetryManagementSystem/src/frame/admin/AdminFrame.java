package frame.admin;

import frame.user.UserMenuPanel;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class AdminFrame extends JFrame{
    public AdminFrame() throws PropertyVetoException {
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setContentPane(new AdminMenuPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
