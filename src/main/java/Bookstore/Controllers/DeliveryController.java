package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Delivery;
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
    public Delivery getDeliverybyId(@PathVariable String idOrder){
        Delivery delivery = deliveryRepository.findById(idOrder);
        return delivery;
    }

    // Create a new delivery

    @PostMapping
    public Delivery creatDelivery(@RequestBody Delivery delivery){
        delivery.setShippingDate(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    /*  Update a delivery
    @PutMapping("/{idOrder}")
    public Delivery updateDelivery(@PathVariable String idOrder, @RequestBody Delivery updatedDelivery) {
        Delivery existingDelivery = deliveryRepository.findById(idOrder);

        if (existingDelivery.existsById(idOrder)) {
            throw new RuntimeException("Delivery not found");
        }

        // Update fields of the existing delivery with new values
        Delivery delivery = existingDelivery.get();
        delivery.setIdBook(updatedDelivery.getIdBook());
        delivery.setShipped(updatedDelivery.isShipped());
        delivery.setShippingDate(updatedDelivery.getShippingDate());
        
        return deliveryRepository.save(delivery);
    } */

    // Deleted delivery
    @DeleteMapping
    public void deleteDelivery(@PathVariable String idOrder){
        if (!deliveryRepository.existsById(idOrder)) {
            throw new RuntimeException("Delivery not found");
        }
        deliveryRepository.deleteById(idOrder);
    }
}
