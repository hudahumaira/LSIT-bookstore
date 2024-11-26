package Bookstore.Repositories;

import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class WebsiteRepository {
    private final Map<UUID, Website> websites = new HashMap<>();

    // Check the status of the cart by its ID
    public Website checkCartStatus(UUID cartId, CustomerCart cart) {
        if (cart == null) {
            return new Website(cartId) {{
                setOrderStatus("FAILED - Cart not found.");
            }};
        }

        if (!cart.isPaid()) {
            return new Website(cartId) {{
                setOrderStatus("FAILED - Payment not completed.");
            }};
        }

        if (!cart.hasCustomerDetails()) {
            return new Website(cartId) {{
                setOrderStatus("FAILED - Customer details are incomplete.");
            }};
        }

        return websites.computeIfAbsent(cartId, id -> new Website(id));
    }

    // Update website details
    public void updateWebsite(Website website) {
        websites.put(website.getCartId(), website);
    }
}
