package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Book;
import Bookstore.Repositories.BookRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> listBooks() {
        return bookRepository.list();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") UUID id) {
        return bookRepository.get(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        // Ensure the book has a valid quantity
        UUID newId;
        // Generate a new random UUID and ensure it is not already in use
        do {
            newId = UUID.randomUUID();
        } while (bookRepository.exists(newId));

        book.setId(newId); // Set the new unique ID
        bookRepository.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable("id") UUID id, @RequestBody Book book) {
        book.setId(id);
        // Ensure the book has a valid quantity
        if (book.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        bookRepository.update(book);
        return book;
    }

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

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") UUID id) {
        bookRepository.remove(id);
    }
}
