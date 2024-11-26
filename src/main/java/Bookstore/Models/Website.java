package Bookstore.Models;

import java.util.UUID;

public class Website {
    private final UUID cartId; // Associated cart ID
    private String orderStatus; // Status of the order (e.g., "FAILED", "OK")

    public Website(UUID cartId) {
        this.cartId = cartId;
        this.orderStatus = "PACKED"; // Default status
    }

    public UUID getCartId() {
        return cartId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
