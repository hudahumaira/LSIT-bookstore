
package Bookstore.Controllers;

import Bookstore.Models.Order;
import Bookstore.Models.ManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PutMapping("/approveOrder/{orderId}")
    public Order approveOrder(@PathVariable UUID orderId, @RequestParam UUID cartId) {
        return managementService.approveOrder(orderId, cartId);
    }

    @PutMapping("/rejectOrder/{orderId}")
    public Order rejectOrder(@PathVariable UUID orderId) {
        return managementService.rejectOrder(orderId);
    }

    @PostMapping("/shipOrder/{orderId}")
    public String shipOrder(@PathVariable UUID orderId) {
        return managementService.shipOrder(orderId);
    }

    @PostMapping("/receiveOrder/{orderId}")
    public String receiveOrder(@PathVariable UUID orderId) {
        return managementService.receiveOrder(orderId);
    }
}
