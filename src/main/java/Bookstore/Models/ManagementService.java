package Bookstore.Models;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Models.Order;
import Bookstore.Repositories.BookRepository;
import Bookstore.Repositories.CustomerCartRepository;
import Bookstore.Repositories.DeliveryRepository;
import Bookstore.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManagementService {

    private final OrderRepository orderRepository;
    private final CustomerCartRepository cartRepository;
    private final BookRepository bookRepository;
    private final DeliveryRepository deliveryRepository;

    public ManagementService(OrderRepository orderRepository, CustomerCartRepository cartRepository,
                             BookRepository bookRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public Order approveOrder(UUID orderId, UUID cartId) {
        Order order = orderRepository.get(orderId);
        CustomerCart cart = cartRepository.getCart(cartId);

        if (order == null || cart == null) {
            throw new IllegalArgumentException("Order or cart not found.");
        }

        for (Book bookInCart : cart.getBooks()) {
            Book inventoryBook = bookRepository.get(bookInCart.getId());
            if (inventoryBook == null || inventoryBook.getQuantity() < bookInCart.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for book: " + bookInCart.getTitle());
            }

            // Update inventory
            inventoryBook.setQuantity(inventoryBook.getQuantity() - bookInCart.getQuantity());
            bookRepository.update(inventoryBook);
        }

        order.setApproved(true);
        orderRepository.update(order);
        return order;
    }

    // to reject the order
    public Order rejectOrder(UUID orderId) {
        Order order = orderRepository.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }

        order.setApproved(false);
        orderRepository.update(order);
        return order;
    }

    // checking if the order is ready to ship, if yes ship
    public String shipOrder(UUID orderId) {
        Order order = orderRepository.get(orderId);
        if (order == null || !order.isApproved()) {
            throw new IllegalArgumentException("Order not found or not approved.");
        }

        order.setShipped(true);
        orderRepository.update(order);

        return "Order shipped successfully.";
    }

    public String receiveOrder(UUID orderId) {
        if (!deliveryRepository.existsById(orderId.toString())) {
            throw new IllegalArgumentException("Delivery not found.");
        }

        return "Order marked as received.";
    }
}
