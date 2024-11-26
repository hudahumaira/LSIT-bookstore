package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Delivery;
import Bookstore.Models.Order;
import Bookstore.Models.CustomerCart;
import Bookstore.Models.Book;
import Bookstore.Repositories.DeliveryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private DeliveryRepository deliveryRepository;

    // Get delivery by order
    @GetMapping("/{idOrder}")
    public Delivery getDeliverybyId(@PathVariable UUID idOrder){
        Delivery delivery = deliveryRepository.findById(idOrder);
        return delivery;
    }

    // Create a new delivery
    @PostMapping
    public Delivery createDelivery(@RequestBody Delivery delivery, Order order, CustomerCart cart, Book book){
        delivery = new Delivery(order, cart, book);
        //delivery.setShippingDate(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    //Update a delivery
    @PutMapping("/{idOrder}")
    public void updateDelivery(@PathVariable UUID idOrder, @RequestBody Order order) {

        order.setShipped(true);
        
    }

    // Deleted delivery
    @DeleteMapping
    public void deleteDelivery(@PathVariable UUID idOrder){
        if (!deliveryRepository.existsById(idOrder)) {
            throw new RuntimeException("Delivery not found");
        }
        deliveryRepository.deleteById(idOrder);
    }
}
