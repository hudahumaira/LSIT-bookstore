package Bookstore.Models;

import java.util.UUID;

public class Order {
    private UUID id ;
    private boolean approved;
    private boolean shipped;

    public Order() {

    }

    public Order(UUID id, boolean approved, boolean shipped) {
        this.id = id;
        this.approved = approved;
        this.shipped = shipped;

    }
    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }
}
