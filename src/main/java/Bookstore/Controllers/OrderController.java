package Bookstore.Controllers;

import org.springframework.web.bind.annotation.*;
import Bookstore.Models.Order;
import Bookstore.Repositories.OrderRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderRepository.list();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        orderRepository.add(order);
        return order;
    }

    @PutMapping("/{id}/approve")
    public Order approveOrder(@PathVariable("id") UUID id) {
        Order order = orderRepository.get(id);
        order.setApproved(true);
        orderRepository.update(order);
        return order;
    }

    @PutMapping("/{id}/reject")
    public Order rejectOrder(@PathVariable("id") UUID id) {
        Order order = orderRepository.get(id);
        order.setApproved(false);
        orderRepository.update(order);
        return order;
    }

    @PutMapping("/{id}/ship")
    public Order shipOrder(@PathVariable("id") UUID id) {
        Order order = orderRepository.get(id);
        order.setShipped(true);
        orderRepository.update(order);
        return order;
    }
}
