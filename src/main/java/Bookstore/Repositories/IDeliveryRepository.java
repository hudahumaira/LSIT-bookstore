package Bookstore.Repositories;

import java.util.*;

import Bookstore.Models.Delivery;

public interface IDeliveryRepository {

    Delivery createDelivery(UUID cartId);

    Delivery getDelivery(UUID cartId);

    void updateDelivery(Delivery delivery);

    void removeDelivery(UUID cartId);
}
