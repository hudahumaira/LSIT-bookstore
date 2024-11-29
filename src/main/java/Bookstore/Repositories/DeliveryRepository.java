package Bookstore.Repositories;

import Bookstore.Models.Delivery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class DeliveryRepository implements IDeliveryRepository {
    private final Map<UUID, Delivery> deliveries = new HashMap<>();

    // Create a new delivery associated with a cart
    public Delivery createDelivery(UUID cartId) {
        Delivery delivery = new Delivery(cartId);
        deliveries.put(cartId, delivery);
        return delivery;
    }

    // Get a delivery by cart ID
    public Delivery getDelivery(UUID cartId) {
        return deliveries.get(cartId);
    }

    // Update the delivery status
    public void updateDelivery(Delivery delivery) {
        deliveries.put(delivery.getCartId(), delivery);
    }

    // Remove a delivery by cart ID
    public void removeDelivery(UUID cartId) {
        deliveries.remove(cartId);
    }
}

