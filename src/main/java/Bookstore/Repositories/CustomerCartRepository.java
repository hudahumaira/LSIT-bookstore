package Bookstore.Repositories;

import Bookstore.Models.CustomerCart;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerCartRepository implements ICustomerCartRepository {
    private static final Map<UUID, CustomerCart> carts = new HashMap<>();

    // Create a new cart
    public CustomerCart createCart() {
        CustomerCart cart = new CustomerCart();
        carts.put(cart.getId(), cart);
        return cart;
    }

    // Get a cart by ID
    public CustomerCart getCart(UUID id) {
        return carts.get(id);
    }

    // Get all carts
    public Collection<CustomerCart> getAllCarts() {
        return carts.values();
    }

    // Update a cart
    public void updateCart(CustomerCart cart) {
        carts.put(cart.getId(), cart);
    }

    // Remove a cart
    public void removeCart(UUID id) {
        carts.remove(id);
    }
}
