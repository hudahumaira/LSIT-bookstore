package Bookstore.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerCart {
    private final UUID id; // Unique cart ID
    private String customerUsername; // Username of the customer
    private List<Book> books; // List of books in the cart
    private boolean isPaid; // Payment status
    private double totalPrice; // Total price of books in the cart

    // Customer Information
    private String customerAddress;
    private String customerEmail;

    // Constructor
    public CustomerCart() {
        this.id = UUID.randomUUID(); // Generate a unique ID for the cart
        this.books = new ArrayList<>();
        this.isPaid = false;
        this.totalPrice = 0.0; // Initialize total price
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public List<Book> getBooks() {
        return books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void addBook(Book book) {
        boolean bookExists = false;
        for (Book existingBook : books) {
            if (existingBook.getId().equals(book.getId())) {
                existingBook.setQuantity(existingBook.getQuantity() + book.getQuantity());
                bookExists = true;
                break;
            }
        }

        if (!bookExists) {
            this.books.add(book);
        }

        recalculateTotalPrice();
    }

    public void removeBook(UUID bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        this.totalPrice = books.stream()
                .mapToDouble(book -> book.getPrice() * book.getQuantity())
                .sum();
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void markAsPaid() {
        this.isPaid = true;
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

    // Dynamically check if customer details are complete
    public boolean hasCustomerDetails() {
        return customerAddress != null && !customerAddress.isBlank()
                && customerEmail != null && !customerEmail.isBlank();
    }
}
