package Bookstore.Controllers;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Repositories.BookRepository;
import Bookstore.Repositories.CustomerCartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CustomerCartController {
    private final CustomerCartRepository cartRepository;
    private final BookRepository bookRepository;

    public CustomerCartController(CustomerCartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    // Step 1: Login and create a new cart
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String loginMessage = "Login successful for user: " + username;

        CustomerCart cart = cartRepository.createCart();
        cart.setCustomerUsername(username);

        return loginMessage + ". Your new cart ID is: " + cart.getId().toString();
    }

    // Step 2: Browse Books
    @GetMapping("/books")
    public Collection<Book> browseBooks() {
        return bookRepository.list();
    }

    // Step 3: Add Books to Cart
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
        if (quantity <= 0 || book.getQuantity() < quantity) {
            throw new IllegalArgumentException("Invalid quantity requested.");
        }

        cart.addBook(new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getGenre(), quantity));
        cartRepository.updateCart(cart);

        return cart;
    }

    // Step 4: Enter Customer Address and Email
    @PutMapping("/{cartId}/details")
    public CustomerCart enterDetails(
            @PathVariable UUID cartId,
            @RequestParam String address,
            @RequestParam String email
    ) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        cart.setCustomerAddress(address);
        cart.setCustomerEmail(email);
        cartRepository.updateCart(cart);

        return cart;
    }

    // Step 5: View Total Price
    @GetMapping("/{cartId}/total")
    public String viewTotalPrice(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        double totalPrice = cart.getTotalPrice();

        if (totalPrice == 0) {
            return "Your cart is empty. Add books to see the total price.";
        }

        return "Total price for your cart: $" + totalPrice;
    }

    // Step 6: Pay Cart
    @PostMapping("/{cartId}/pay")
    public String payCart(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }
        if (cart.getBooks().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Add books before payment.");
        }
        if (!cart.hasCustomerDetails()) {
            throw new IllegalArgumentException("Customer details are incomplete. Please enter your details.");
        }

        double totalPrice = cart.getTotalPrice();
        cart.markAsPaid();
        cartRepository.updateCart(cart);

        return "Payment successful! Total amount paid: $" + totalPrice;
    }

    // Step 7: View All Carts with Details
    @GetMapping
    public Collection<Object> getAllCartsWithDetails() {
        return cartRepository.getAllCarts().stream()
                .map(cart -> {
                    return new Object() {
                        public final UUID cartId = cart.getId();
                        public final String username = cart.getCustomerUsername();
                        public final String address = cart.getCustomerAddress();
                        public final String email = cart.getCustomerEmail();
                        public final double totalPrice = cart.getTotalPrice();
                        public final boolean isPaid = cart.isPaid();
                        public final boolean informationComplete = cart.hasCustomerDetails();
                        public final Collection<Object> booksOrdered = cart.getBooks().stream()
                                .map(book -> new Object() {
                                    public final String title = book.getTitle();
                                    public final int quantity = book.getQuantity();
                                }).collect(Collectors.toList());
                    };
                }).collect(Collectors.toList());
    }


    // Step 8: Cancel Order
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
