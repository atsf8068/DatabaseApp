package DatabaseProgram;

import DatabaseProgram.DatabaseEntries.Book;

import java.sql.*;
import java.util.ArrayList;

public class JdbcSelectTest {
    public ArrayList<Book> books = new ArrayList<>();
    public void selectTest(String username, char[] password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                username, String.valueOf(password));
        Statement stmt = conn.createStatement();
        String strSelect = "select id, title, author, price, qty from books";
        System.out.println("The sql statement is " + strSelect + "\n");
        ResultSet rSet = stmt.executeQuery(strSelect);
        System.out.println("The records selected are:");
        int rowcount = 0;
        Book book;
        while (rSet.next()) {
            book = new Book(rSet.getInt("id"), rSet.getString("title"),
                    rSet.getString("author"), rSet.getFloat("price"),
                    rSet.getInt("qty"));
            books.add(book);
            String title = rSet.getString("title");
            double price = rSet.getDouble("price");
            int quantity = rSet.getInt("qty");
            System.out.println(title + "," + price + "," + quantity);
            rowcount++;
        }
        System.out.println("Total number of records:" + rowcount);
    }
}
