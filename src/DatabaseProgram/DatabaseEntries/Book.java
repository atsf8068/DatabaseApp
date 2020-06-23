package DatabaseProgram.DatabaseEntries;

public class Book {
    public int id, qty;
    public String title, author;
    public float price;

    public Book(int id, String title, String author, float price, int qty) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }
}
