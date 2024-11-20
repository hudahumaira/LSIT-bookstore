package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Book;
import Bookstore.Repositories.BookRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

// TO DO: add swagger annotations for all controllers

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
        bookRepository.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable("id") UUID id, @RequestBody Book book) {
        book.setId(id);
        bookRepository.update(book);
        return book;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") UUID id) {
        bookRepository.remove(id);
    }
}
