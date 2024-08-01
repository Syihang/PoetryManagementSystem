package frame.user;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class UserFrame extends JFrame {
    public UserFrame() throws PropertyVetoException {
        this.setTitle("¿Í»§¶Ë");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setContentPane(new UserMenuPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
