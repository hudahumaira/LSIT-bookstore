package Bookstore.Repositories.Testing;

import Bookstore.Models.Book;
import Bookstore.Repositories.S3BookRepository;

import java.util.UUID;

public class S3BookRepositoryIntegrationTest {
    public static void main(String[] args) {
        // Initialize the S3BookRepository
        S3BookRepository s3BookRepository = new S3BookRepository();
        /*
        // Create a test book
        Book newBook = new Book();
        newBook.setId(UUID.randomUUID());
        newBook.setTitle("Test Book");
        newBook.setQuantity(10);

        // Add the book to S3 (to your test bucket)
        s3BookRepository.add(newBook); */
        
        // Retrieve the book from S3
        String uuidString = "9f04877f-3f17-4069-9382-e06a53af8c01";
        UUID bookId = UUID.fromString(uuidString);
        Book retrievedBook = s3BookRepository.get(bookId);

        // Print out the retrieved book's details
        if (retrievedBook != null) {
            System.out.println("Retrieved Book: " + retrievedBook.getTitle() + ", Quantity: " + retrievedBook.getQuantity());
        } else {
            System.out.println("Book not found!");
        }
        /*
        // Update the book's quantity
        retrievedBook.setQuantity(20);
        s3BookRepository.update(retrievedBook);

        // Retrieve the updated book
        Book updatedBook = s3BookRepository.get(bookId);

        // Print out the updated book's details
        if (updatedBook != null) {
            System.out.println("Updated Book: " + updatedBook.getTitle() + ", Quantity: " + updatedBook.getQuantity());
        } else {
            System.out.println("Updated book not found!");
        }

        // Remove the book from S3
        s3BookRepository.remove(bookId);

        // Try to retrieve the book after removal
        Book removedBook = s3BookRepository.get(bookId);
        if (removedBook == null) {
            System.out.println("Book successfully removed!");
        } else {
            System.out.println("Book still exists in S3.");
        } */
    }
}

