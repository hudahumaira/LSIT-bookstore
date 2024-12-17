package Bookstore.Repositories.Testing;

import java.util.UUID;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Repositories.S3CustomerCartRepository;


public class S3CustomerCartRepositoryIntegrationTest {
    public static void main(String[] args) {
        // Create an instance of the repository
        S3CustomerCartRepository cartRepository = new S3CustomerCartRepository();

        // 1. Test createCart method
        //UUID cartId = UUID.randomUUID(); // Create a new random cart ID
        //System.out.println("Testing createCart method...");
        //CustomerCart cart = cartRepository.createCart();

        //System.out.println("Created Cart: " + cart);
        //assert cart != null : "Cart should not be null";
        //assert cart.getId() != null : "Cart ID should not be null";

        String id = "cb875e1e-2be5-49aa-b167-0fda610d177c";
        UUID cartId = UUID.fromString(id);
        CustomerCart cart = cartRepository.getCart(cartId);

        // 2. Test addBook to the cart
        System.out.println("Testing addBook method...");
        // Create a sample book
        Book book = new Book(UUID.randomUUID(), "The Great Book", "Ingrid", 19.99, "Science", 2); // Assuming Book class exists
        cart.addBook(book);
        cartRepository.updateCart(cart);
        System.out.println("Updated Cart with new book: " + cart.getBooks());

        // 3. Test getCart method to fetch the cart from cloud
        System.out.println("Testing getCart method...");
        CustomerCart fetchedCart = cartRepository.getCart(cartId);
        System.out.println("Fetched Cart: " + fetchedCart);
         

        // 5. Test getAllCarts method (retrieve all carts from cloud)
        System.out.println("Testing getAllCarts method...");
        var allCarts = cartRepository.getAllCarts();
        System.out.println("All Carts in the cloud: ");
        allCarts.forEach(c -> System.out.println(c));

        // 6. Test removeCart method (delete cart from the cloud)
        //System.out.println("Testing removeCart method...");
        //String id = "407add60-92c1-40b2-82df-7652edc7b47b";
        //UUID cartId = UUID.fromString(id);
        //cartRepository.removeCart(cartId);

        
    }
}

