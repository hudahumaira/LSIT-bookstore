package Bookstore.Repositories;

import java.util.*;

import Bookstore.Models.Book;

 public interface IBookRepository {
    boolean exists(UUID id);

    List<Book> list();

    Book get(UUID id);

    void add(Book book);

    void update(Book book);

    void updateQuantity(UUID id, int quantity);

    void remove(UUID id);


 }