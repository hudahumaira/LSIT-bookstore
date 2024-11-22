package Bookstore.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerCart {
    private final UUID id; // Unique cart ID
    private List<Book> books; // List of books in the cart
    private boolean isPaid; // Payment status

    // Customer Information
    private String customerName;
    private String customerAddress;
    private String customerEmail;

    // Constructor
    public CustomerCart() {
        this.id = UUID.randomUUID(); // Generate a unique ID for the cart
        this.books = new ArrayList<>();
        this.isPaid = false;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(UUID bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    // Validation to check if customer details are complete
    public boolean isCustomerInfoComplete() {
        return customerName != null && !customerName.isBlank() &&
               customerAddress != null && !customerAddress.isBlank() &&
               customerEmail != null && !customerEmail.isBlank();
    }
}

