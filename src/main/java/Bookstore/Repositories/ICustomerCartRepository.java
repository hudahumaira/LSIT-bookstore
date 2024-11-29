package Bookstore.Repositories;

import Bookstore.Models.CustomerCart;

import java.util.Collection;
import java.util.UUID;

public interface ICustomerCartRepository {

    CustomerCart createCart();

    CustomerCart getCart(UUID id);

    Collection<CustomerCart> getAllCarts();

    void updateCart(CustomerCart cart);

    void removeCart(UUID id);
}
