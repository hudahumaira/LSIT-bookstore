package Bookstore.Controllers;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;
import Bookstore.Repositories.BookRepository;
import Bookstore.Repositories.CustomerCartRepository;
import Bookstore.Repositories.S3BookRepository;
import Bookstore.Repositories.S3CustomerCartRepository;
import Bookstore.Repositories.S3WebRepository;
import Bookstore.Repositories.WebsiteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private final S3BookRepository bookRepository;
    private final S3CustomerCartRepository cartRepository;
    private final S3WebRepository websiteRepository;

    public DeliveryController(S3BookRepository bookRepository, S3CustomerCartRepository cartRepository, S3WebRepository websiteRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
        this.websiteRepository = websiteRepository;
    }

    // Deliver books by updating the inventory and marking the delivery as ready
    @PostMapping("/deliver/{cartId}")
    public String deliverBooks(@PathVariable UUID cartId) {
        // Get the associated cart
        CustomerCart cart = cartRepository.getCart(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found.");
        }

        if (!cart.isPaid()) {
            throw new IllegalArgumentException("Payment not completed. Cannot proceed with delivery.");
        }

        // Update inventory for books in the cart
        for (Book cartBook : cart.getBooks()) {
            Book storeBook = bookRepository.get(cartBook.getId());
            if (storeBook == null) {
                throw new IllegalArgumentException("Book not found in inventory: " + cartBook.getTitle());
            }

            if (storeBook.getQuantity() < cartBook.getQuantity()) {
                throw new IllegalArgumentException("Not enough inventory for book: " + cartBook.getTitle());
            }

            // Update inventory
            storeBook.setQuantity(storeBook.getQuantity() - cartBook.getQuantity());
            bookRepository.update(storeBook);
        }

        // Get the corresponding Website entry and update the status
        Website website = websiteRepository.checkCartStatus(cartId, cart);
        if (website == null) {
            throw new IllegalArgumentException("Website entry not found for cart ID: " + cartId);
        }

        // Update the Website status to DELIVERED
        website.setOrderStatus("PACKED and DELIVERED");
        websiteRepository.updateWebsite(website);

        return "Book inventory updated successfully. Delivery is now marked as DELIVERED. Cart ID: " + cartId;
    }
}
