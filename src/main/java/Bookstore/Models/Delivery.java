package Bookstore.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Delivery {

    private String idOrder;
    //private String trackingNumber; // Not the same as idOrder?

    private UUID idBook;
    private LocalDateTime shippingDate;
    private boolean shipped;

    
    //Constructor
    public Delivery(){

    }

    public Delivery(String idOrder, UUID idBook, Boolean shipped ){
        this.idOrder = idOrder;
        this.idBook = idBook;
        this.shipped = shipped;

    }

    //Getters and setters
    public String getIdOrder(){
        return idOrder;
    }

    public void setIdOrder(String idOrder){
        this.idOrder = idOrder;
    }

    public UUID getIdBook(){
        return idBook;

    }

    public void setIdBook(UUID idBook) {
        this.idBook = idBook;
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
