package Bookstore.Repositories;

import Bookstore.Utils.SampleBooks;
import Bookstore.Models.Book;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class BookRepository {
    private final Map<UUID, Book> books = new HashMap<>();

    public BookRepository() {
        // Load sample books into the repository
        SampleBooks.getBooks().forEach(book -> books.put(book.getId(), book));
    }

    public List<Book> list() {
        return new ArrayList<>(books.values());
    }

    public Book get(UUID id) {
        return books.get(id);
    }

    public void add(Book book) {
        book.setId(UUID.randomUUID());
        books.put(book.getId(), book);
    }

    // TODO: Implement the update method like the teacher did

    public void update(Book book) {
        books.put(book.getId(), book);
    }

    public void remove(UUID id) {
        books.remove(id);
    }
}
