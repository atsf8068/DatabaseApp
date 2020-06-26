import Database.DatabaseCommands;

import javax.swing.*;
import java.sql.SQLException;

public class AddEntryWindow extends JFrame{
    private JTextField IDField;
    private JTextField TitleField;
    private JTextField AuthorField;
    private JTextField PriceField;
    private JTextField QuantityField;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel IDLabel;
    private JLabel TitleLabel;
    private JLabel AuthorLabel;
    private JLabel PriceLabel;
    private JLabel QuantityLabel;
    private JLabel AddEntryLabel;
    private JPanel mainPanel;
    private JPanel FieldsPanel;
    private JPanel ButtonsPanel;
    private JPanel TitlePanel;

    public AddEntryWindow(DatabaseCommands commands, MainWindow.TableModel model) {
        this.setTitle("AddEntry");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        IDField.addActionListener(e -> TitleField.requestFocus());
        TitleField.addActionListener(e -> AuthorField.requestFocus());
        AuthorField.addActionListener(e -> PriceField.requestFocus());
        PriceField.addActionListener(e -> QuantityField.requestFocus());
        QuantityField.addActionListener(e -> addEntrySequence(model, commands));
        addButton.addActionListener(e -> addEntrySequence(model, commands));
        cancelButton.addActionListener(e -> this.dispose());
    }

    private void addEntrySequence(MainWindow.TableModel model, DatabaseCommands commands) {
        try {
            int id = Integer.parseInt(IDField.getText());
            String title = TitleField.getText();
            String author = AuthorField.getText();
            float price = Float.parseFloat(PriceField.getText());
            int qty = Integer.parseInt(QuantityField.getText());
            commands.insertEntry(id, title, author, price, qty);
            Object[] row = new Object[5];
            row[0] = id;
            row[1] = title;
            row[2] = author;
            row[3] = price;
            row[4] = qty;
            model.addRow(row);
            this.dispose();
        } catch (NumberFormatException throwable) {
            JOptionPane.showMessageDialog(AddEntryWindow.this, "Entered a non number input in ID, price or quantity", "Input Error", JOptionPane.ERROR_MESSAGE);
            throwable.printStackTrace();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
