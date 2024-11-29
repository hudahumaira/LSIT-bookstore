package Bookstore.Repositories;

import Bookstore.Utils.BookInventory;
import Bookstore.Models.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements IBookRepository {
    private final Map<UUID, Book> books = new HashMap<>();

    public BookRepository() {
        // Load sample books into the repository
        BookInventory.getBooks().forEach(book -> books.put(book.getId(), book));
    }

    public boolean exists(UUID id) {
        return books.containsKey(id);
    }

    public List<Book> list() {
        return new ArrayList<>(books.values());
    }

    public Book get(UUID id) {
        return books.get(id);
    }

    public void add(Book book) {
        if (book.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        books.put(book.getId(), book);
    }

    public void update(Book book) {
        // Ensure the book has a valid quantity
        if (book.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        books.put(book.getId(), book);
    }

    public void updateQuantity(UUID id, int quantity) {
        // Retrieve the book and update its quantity
        Book book = books.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        book.setQuantity(quantity);
        books.put(book.getId(), book);
    }

    public void remove(UUID id) {
        books.remove(id);
    }
}
