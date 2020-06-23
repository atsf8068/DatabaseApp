import DatabaseProgram.DatabaseCommands;
import DatabaseProgram.DatabaseEntries.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JButton addNewEntryButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JComboBox comboBox1;
    private JTable table1;
    private JTable table2;
    private JRadioButton EditButton;

    public MainWindow(DatabaseCommands commands, ArrayList<Book> books) {
        this.setTitle("Main Window");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TableModel model = new TableModel(books);
        table1.setModel(model);
        table2.setModel(model);
        table1.setAutoCreateRowSorter(true);
        table2.setAutoCreateRowSorter(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        addNewEntryButton.addActionListener(e -> new AddEntryWindow(commands, model));
    }

    static class TableModel extends DefaultTableModel {

        public TableModel(ArrayList<Book> data) {
            Object[] rowData = new Object[5];
            String[] columnNames = {"ID", "Title", "Author", "Price", "Quantity"};
            this.setColumnIdentifiers(columnNames);
            for (Book datum : data) {
                rowData[0] = datum.id;
                rowData[1] = datum.title;
                rowData[2] = datum.author;
                rowData[3] = datum.price;
                rowData[4] = datum.qty;
                this.addRow(rowData);
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
