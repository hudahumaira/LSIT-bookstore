package Bookstore.Models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Delivery {

    //private String trackingNumber; Is not the same as idOrder?
    private UUID idOrder;
    private UUID idCart;
    private List<Book> books;
    private LocalDateTime shippingDate;
    private boolean shipped;

    
    //Constructor
    public Delivery(){

    }

    public Delivery(Order order, CustomerCart cart, Book book){
        this.idOrder = order.getId();
        this.idCart = cart.getId();
        this.books = cart.getBooks();
        this.shipped = order.isShipped();

    }

    //Getters and setters
    public UUID getIdOrder(){
        return idOrder;
    }

    public void setIdOrder(UUID idOrder){
        this.idOrder = idOrder;
    }

    public UUID getIdCart(){
        return idCart;
    }
    
    public void getIdCart(UUID idCart){
        this.idCart = idCart;
    }

    public List<Book> getBooks(){
        return books;
    }

    public LocalDateTime getShippingDate(){
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate){
        this.shippingDate = shippingDate;
    }

}
