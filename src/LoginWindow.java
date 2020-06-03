import DatabaseProgram.JdbcSelectTest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginWindow {
    static JFrame f;
    private JPanel LoginPanel;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;

    public LoginWindow() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JdbcSelectTest selectTest = new JdbcSelectTest();
                    System.out.println(password.getPassword());
                    selectTest.selectTest(username.getText(), password.getPassword());
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Login Error", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        f = new JFrame("LoginWindow");
        f.setContentPane(new LoginWindow().LoginPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
