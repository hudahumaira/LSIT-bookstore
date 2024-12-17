package Bookstore.Repositories.Testing;

import java.util.UUID;
import Bookstore.Models.Delivery;
import software.amazon.awssdk.services.s3.model.*;
import Bookstore.Repositories.S3DeliveryRepository;

public class S3DeliveryRepositoryIntegrationTest {
    public static void main(String[] args) {
        // Create an instance of the repository
        S3DeliveryRepository deliveryRepository = new S3DeliveryRepository();
        
        // 1. Test createDelivery method
        //UUID cartId = UUID.randomUUID(); // Use a random UUID for the cart ID
        //System.out.println("Testing createDelivery method...");
        //Delivery delivery = deliveryRepository.createDelivery(cartId);
        //System.out.println("Created Delivery: " + delivery);

        String id = "327b695a-5823-4774-afeb-04c6f686ccca";
        UUID deliveryId = UUID.fromString(id);
        
        // 2. Test getDelivery method
        System.out.println("Testing getDelivery method...");
        Delivery fetchedDelivery = deliveryRepository.getDelivery(deliveryId);
        System.out.println("Fetched Delivery: " + fetchedDelivery);
        
        
        // 3. Test updateDelivery method
        System.out.println("Testing updateDelivery method...");
        fetchedDelivery.setStatus("Shipped");
        deliveryRepository.updateDelivery(fetchedDelivery);
        Delivery updatedDelivery = deliveryRepository.getDelivery(deliveryId);
        System.out.println("Updated Delivery: " + updatedDelivery);
        

        // 4. Test removeDelivery method
        System.out.println("Testing removeDelivery method...");
        deliveryRepository.removeDelivery(deliveryId);
         
    }
}
