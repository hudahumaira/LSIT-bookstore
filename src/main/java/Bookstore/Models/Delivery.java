package Bookstore.Models;

import java.util.UUID;

public class Delivery {
    private UUID cartId; // Associated cart ID
    private String status; // Delivery status (e.g., "PENDING", "PACKED and DELIVERED")

    public Delivery() {
        this.status = "PENDING";
    }

    public Delivery(UUID cartId) {
        this.cartId = cartId;
        this.status = "PENDING"; // Default status
    }

    public UUID getCartId() {
        return cartId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
