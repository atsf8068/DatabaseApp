package Database;

import Database.DatabaseEntries.Book;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommands {
    public Connection connection;

    public void getConnection(String username, char[] password) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                username, String.valueOf(password));
    }

    public ArrayList<Book> loadTables() throws SQLException {
        Statement stmt = connection.createStatement();
        String strSelect = "select id, title, author, price, qty from books";
        ResultSet rSet = stmt.executeQuery(strSelect);
        ArrayList<Book> books = new ArrayList<>();
        while (rSet.next()) {
            Book book = new Book(rSet.getInt("id"), rSet.getString("title"),
                    rSet.getString("author"), rSet.getFloat("price"),
                    rSet.getInt("qty"));
            books.add(book);
        }
        return books;
    }

    public void insertEntry(int id, String title, String author, float price, int qty) throws SQLException {
        String strInsert = "insert into books values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(strInsert);
        preparedStmt.setInt(1, id);
        preparedStmt.setString(2, title);
        preparedStmt.setString(3, author);
        preparedStmt.setFloat(4, price);
        preparedStmt.setInt(5, qty);
        preparedStmt.execute();
    }

    public void updateCell(String id, String data, String columnName) throws SQLException {
        String databaseColName = getDatabaseColumnName(columnName);
        String strUpdate = "update books set " + databaseColName + " = ? where id = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(strUpdate);
        switch (databaseColName) {
            case "id":
            case "qty":
                preparedStmt.setInt(1, Integer.parseInt(data));
                break;
            case "title":
            case "author":
                preparedStmt.setString(1, data);
                break;
            case "price":
                preparedStmt.setFloat(1, Float.parseFloat(data));
                break;
        }
        preparedStmt.setInt(2, Integer.parseInt(id));
        preparedStmt.execute();
    }

    private String getDatabaseColumnName(String columnName) {
        switch (columnName) {
            case "ID":
                return "id";
            case "Title":
                return "title";
            case "Author":
                return "author";
            case "Price":
                return "price";
            case "Quantity":
                return "qty";
        }
        return null;
    }
}
