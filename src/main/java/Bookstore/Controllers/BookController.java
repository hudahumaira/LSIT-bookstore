package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Book;
import Bookstore.Repositories.BookRepository;
import Bookstore.Repositories.S3BookRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private final S3BookRepository bookRepository;

    public BookController(S3BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // Step 1: Get details of a specific book
    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") UUID id) {
        Book book = bookRepository.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        return book;
    }

    // Step 2: Add a new book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        // Ensure the book has a valid quantity
        if (book.getQuantity() < 0) {
            throw new IllegalArgumentException("Book quantity cannot be negative.");
        }

        UUID newId;
        // Generate a new random UUID and ensure it is not already in use
        do {
            newId = UUID.randomUUID();
        } while (bookRepository.exists(newId));

        book.setId(newId); // Set the new unique ID
        bookRepository.add(book);
        return book;
    }

    // Step 3: Update the quantity of an existing book
    @PutMapping("/{id}/updateQuantity/{quantity}")
    public Book updateBookQuantity(@PathVariable("id") UUID id, @PathVariable("quantity") int quantity) {
        Book book = bookRepository.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        book.setQuantity(quantity);
        bookRepository.update(book);
        return book;
    }

    // Step 4: Delete a book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") UUID id) {
        Book book = bookRepository.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        bookRepository.remove(id);
    }
}
