package Bookstore.Repositories;

import Bookstore.Models.Delivery;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DeliveryRepository {

    private static Map<UUID, Delivery> deliveries = new HashMap<>();

    // Create a delivery
    public Delivery save(Delivery delivery){
        if(delivery.getIdOrder() == null){
            delivery.setIdOrder(UUID.randomUUID());
        }

        deliveries.put(delivery.getIdOrder(), delivery);

        return delivery;
    }

    // Find a delivery by idOrder
    public Delivery findById(UUID idOrder){
        return deliveries.get(idOrder);
    }

    // Check if a delivery exist by idOrder
    public boolean existsById(UUID idOrder){
        return deliveries.containsKey(idOrder);
    }

    // Delete a delivery by idOrder
    public void deleteById(UUID idOrder){
        deliveries.remove(idOrder);
    }
    
}
