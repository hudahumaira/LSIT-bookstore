package Bookstore.Models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Delivery {

    //private String trackingNumber; Is not the same as idOrder?
    private String idOrder;
    private final Map<UUID, Book> idBooks = new HashMap<>();
    private LocalDateTime shippingDate;
    private boolean shipped;

    
    //Constructor
    public Delivery(){

    }

    public Delivery(String idOrder, UUID idBook, Book book, Boolean shipped ){
        this.idOrder = idOrder;
        this.idBooks.put(idBook, book);
        this.shipped = shipped;

    }

    //Getters and setters
    public String getIdOrder(){
        return idOrder;
    }

    public void setIdOrder(String idOrder){
        this.idOrder = idOrder;
    }

    public Map<UUID, Book> getIdBooks(){
        return idBooks;

    }

    public void setIdBook(Map<UUID, Book> idBooks) {
        this.idBooks.putAll(idBooks);
    }
    
    public void addBook(UUID idBook, Book book) {
        this.idBooks.put(idBook, book);
    }

    public LocalDateTime getShippingDate(){
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate){
        this.shippingDate = shippingDate;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }
}
