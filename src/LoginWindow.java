import DatabaseProgram.DatabaseCommands;
import DatabaseProgram.JdbcSelectTest;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel panel1;

    public LoginWindow(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(title);
        panel1.setBackground(Color.BLUE);
        this.setContentPane(panel1);
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> {
            // do the login procedure
            loginSequence(usernameField.getText(), passwordField.getPassword());
        });
        loginButton.addActionListener(e -> loginSequence(usernameField.getText(), passwordField.getPassword()));
    }

    private void loginSequence(String username, char[] password) {
        try {
            DatabaseCommands commands = new DatabaseCommands();
            commands.getConnection(username, password);
            // make a new window
            this.dispose();
            new MainWindow(commands, commands.loadTables());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            JOptionPane.showMessageDialog(LoginWindow.this, "Login Error", "Inane Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new LoginWindow("LoginWindow");
        frame.setVisible(true);
    }
}
