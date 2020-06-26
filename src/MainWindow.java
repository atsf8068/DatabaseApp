import Database.DatabaseCommands;
import Database.DatabaseEntries.Book;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        setTableModel(table1, model);
        setTableModel(table2, model);
        setSorter(table1);
        setSorter(table2);
        setTableSelection(table1);
        setTableSelection(table2);
        new Listener(commands);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        addNewEntryButton.addActionListener(e -> new AddEntryWindow(commands, model));
        EditButton.addActionListener(e -> determineEditMode(model));
    }

    private void setTableModel(JTable table, TableModel model) {
        table.setModel(model);
    }

    private void setTableSelection(JTable table) {
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
    }

    private void setSorter(JTable table) {
        TableRowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void determineEditMode(TableModel model) {
        model.editable = EditButton.isSelected();
        if (!model.editable) {
            if (table1.getCellEditor() != null) {
                table1.getCellEditor().cancelCellEditing();
            } else {
                table2.getCellEditor().cancelCellEditing();
            }
        }
    }

    static class TableModel extends DefaultTableModel {
        private boolean editable = false;

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
            return editable;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 1:
                case 2:
                    return String.class;
                case 3:
                    return Float.class;
                default:
                    return Integer.class;
            }
        }

    }

    private class Listener implements TableModelListener {
        private final DatabaseCommands commands;
        public Listener(DatabaseCommands commands) {
            this.commands = commands;
            table1.getModel().addTableModelListener(this);
            table2.getModel().addTableModelListener(this);
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            if (EditButton.isSelected()) {
                try {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    TableModel model = (TableModel) e.getSource();
                    String data = model.getValueAt(row, column).toString();
                    String id = model.getValueAt(row, 0).toString();
                    String columnName = model.getColumnName(column);
                    commands.updateCell(id, data, columnName);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}
