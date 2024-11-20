package Bookstore.Utils;

import Bookstore.Models.Book;
import java.util.List;
import java.util.UUID;

public class SampleBooks {
    public static List<Book> getBooks() {
        return List.of(
                new Book(UUID.fromString("1d5e9b45-c891-4f2b-a5c1-ec9fce1b9b77"), "To Kill a Mockingbird", "Harper Lee", 12.99, "Fiction"),
                new Book(UUID.fromString("39f68982-4171-4be4-b7dd-b3f6f11c7406"), "1984", "George Orwell", 9.99, "Dystopian"),
                new Book(UUID.fromString("8a0f91a9-cc33-4f88-bac4-0a517c7cb5e3"), "The Great Gatsby", "F. Scott Fitzgerald", 10.99, "Classic"),
                new Book(UUID.fromString("f843dd5a-998e-4716-9534-fac4b9391f45"), "Pride and Prejudice", "Jane Austen", 8.99, "Romance"),
                new Book(UUID.fromString("9d18bc22-f7df-42d6-8cb7-c68f38b6c27d"), "The Catcher in the Rye", "J.D. Salinger", 11.50, "Fiction"),
                new Book(UUID.fromString("2a7b8d4d-933b-466c-b762-d13b9c72f563"), "The Hobbit", "J.R.R. Tolkien", 13.99, "Fantasy"),
                new Book(UUID.fromString("6ebf508c-6c94-4e9d-9393-7ec1a7f7c2a7"), "Moby-Dick", "Herman Melville", 14.99, "Adventure"),
                new Book(UUID.fromString("fcf5c58d-c5b6-4cb7-bdbc-2c9c6d7680e6"), "The Alchemist", "Paulo Coelho", 9.75, "Philosophical Fiction"),
                new Book(UUID.fromString("4173a129-9b45-4c21-8127-ec785e734ed2"), "Brave New World", "Aldous Huxley", 12.50, "Science Fiction"),
                new Book(UUID.fromString("01e3cc29-7002-4aa3-8c1a-bbf07c172680"), "The Road", "Cormac McCarthy", 10.25, "Post-Apocalyptic Fiction")
        );
    }
}
