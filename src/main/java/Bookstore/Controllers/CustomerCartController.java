package Bookstore.Controllers;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Repositories.BookRepository;
import Bookstore.Repositories.CustomerCartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CustomerCartController {
    private final CustomerCartRepository cartRepository;
    private final BookRepository bookRepository;

    public CustomerCartController(CustomerCartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    // Create a new cart with customer details
    @PostMapping
    public CustomerCart createCart(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String email
    ) {
        CustomerCart cart = cartRepository.createCart();
        cart.setCustomerName(name);
        cart.setCustomerAddress(address);
        cart.setCustomerEmail(email);
        cartRepository.updateCart(cart);
        return cart;
    }

    // Add a book to the cart with specified quantity
    @PutMapping("/{cartId}/add/{bookId}")
    public CustomerCart addBookToCart(@PathVariable UUID cartId, @PathVariable UUID bookId, @RequestParam int quantity) {
        CustomerCart cart = cartRepository.getCart(cartId);
        Book book = bookRepository.get(bookId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        if (book == null) {
            throw new IllegalArgumentException("Invalid book ID.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (book.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available for the requested quantity.");
        }

        // Add the book to the cart with the specified quantity
        Book bookToAdd = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getGenre(), quantity);
        cart.addBook(bookToAdd);
        cartRepository.updateCart(cart);

        return cart;
    }

    // View all carts (orders)
    @GetMapping
    public Collection<CustomerCart> getAllCarts() {
        return cartRepository.getAllCarts();
    }

    // View cart details
    @GetMapping("/{cartId}")
    public CustomerCart viewCart(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }
        return cart;
    }

    // Mark a cart as paid (no validation for approval)
    @PostMapping("/{cartId}/pay")
    public String payCart(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        cart.markAsPaid();
        cartRepository.updateCart(cart);
        return "Payment status updated to 'Paid'.";
    }

    // TODO: Implement this method after shipping is implemented
    @PostMapping("/{cartId}/receive")
    public String receiveOrder(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        return "Order marked as received.";
    }

    // Cancel the order (delete the cart)
    @DeleteMapping("/{cartId}")
    public String cancelOrder(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        cartRepository.removeCart(cartId);
        return "Order canceled.";
    }
}
