package DatabaseProgram;

import DatabaseProgram.DatabaseEntries.Book;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommands {
    public Connection connection;

    public void getConnection(String username, char[] password) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                username, String.valueOf(password));
    }

    public void updateDate() throws SQLException {
        Statement stmt = connection.createStatement();
        String strUpdate = "update books set price = price*0.7, qty = qty+1 where id = 1001";
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
}
