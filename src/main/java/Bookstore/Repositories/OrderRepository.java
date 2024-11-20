package Bookstore.Repositories;

import Bookstore.Models.Order;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private final Map<UUID, Order> orders = new HashMap<>();

    public List<Order> list() {
        return new ArrayList<>(orders.values());
    }

    public Order get(UUID id) {
        return orders.get(id);
    }

    public void add(Order order) {
        order.setId(UUID.randomUUID());
        orders.put(order.getId(), order);
    }

    public void update(Order order) {
        orders.put(order.getId(), order);
    }

    public void remove(UUID id) {
        orders.remove(id);
    }
}
