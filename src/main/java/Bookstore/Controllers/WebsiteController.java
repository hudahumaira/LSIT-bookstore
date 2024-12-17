package Bookstore.Controllers;

import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;
import Bookstore.Repositories.CustomerCartRepository;
import Bookstore.Repositories.S3CustomerCartRepository;
import Bookstore.Repositories.S3WebRepository;
import Bookstore.Repositories.WebsiteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/website")
public class WebsiteController {
    private final S3WebRepository websiteRepository;
    private final S3CustomerCartRepository cartRepository;

    public WebsiteController(S3WebRepository websiteRepository, S3CustomerCartRepository cartRepository) {
        this.websiteRepository = websiteRepository;
        this.cartRepository = cartRepository;
    }

    // Check the status of a cart using cart ID
    @GetMapping("/check/{cartId}")
    public Website checkCartStatus(@PathVariable UUID cartId) {
        CustomerCart cart = cartRepository.getCart(cartId);
        return websiteRepository.checkCartStatus(cartId, cart);
    }
}
