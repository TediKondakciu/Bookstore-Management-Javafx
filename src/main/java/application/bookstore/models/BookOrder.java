package application.bookstore.models;

import java.io.*;

public  class BookOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private String title;
    private int quantity;
    private String bookISBN;
    private float unitPrice;
    private Author author;
    private transient Book book;

    public BookOrder(int quantity, Book b) {
        book=b;
        setQuantity(quantity);
        setBookISBN(b.getIsbn());
        setTitle(b.getTitle());
        setUnitPrice(b.getSellingPrice());
        setAuthor(b.getAuthor());
    }


    @Override
    public String toString() {
        return String.format("%-20s %-6s Unit: %-6.2f Total price: %.2f", title, quantity+"pcs", unitPrice, getTotalPrice());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalPrice() {
        return quantity*unitPrice;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }
}
