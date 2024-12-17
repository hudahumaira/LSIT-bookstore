package Bookstore.Repositories.Testing;

import java.util.UUID;

import Bookstore.Models.Book;
import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;
import Bookstore.Repositories.CustomerCartRepository;
import Bookstore.Repositories.S3WebRepository;

public class S3WebRepositoryIntegrationTest {
    public static void main(String[] args) {
        S3WebRepository repository = new S3WebRepository();

        // Step 1: Create a dummy CustomerCart and Website
        //CustomerCart cart = new CustomerCart();
        //cart.setCustomerUsername("JohnDoe");
        //cart.setCustomerEmail("johndoe@example.com");
        //cart.setCustomerAddress("123 Elm Street, Springfield, USA");

        //System.out.println(cart.getId());
/*

        // Step 2: Check Cart Status (should create a new Website for the cart)
        Website websiteStatus = repository.checkCartStatus(cart.getId(), cart);
        System.out.println("Step 2 - Website status after cart check:");
        System.out.println(websiteStatus);

        
        // Step 3: Update and Save the Website
        websiteStatus.setOrderStatus("Order confirmed, preparing for delivery.");
        repository.updateWebsite(websiteStatus);
        System.out.println("Step 3 - Updated Website saved to S3."); */
/* 
        // Step 4: Fetch the Website back from S3
        String id = "82196b33-6da6-45fe-bad2-6f3611a88579";
        UUID cartId = UUID.fromString(id);
        CustomerCartRepository cart = cart.getCart(cartId)
        Website fetchedWebsite = repository.checkCartStatus(cartId, cart);
        System.out.println("Step 4 - Fetched Website from S3:");
        System.out.println(fetchedWebsite); */
/*
        // Step 5: Simulate a cart without payment
        CustomerCart unpaidCart = new CustomerCart(cartId);
        unpaidCart.setPaid(false);

        Website unpaidWebsiteStatus = repository.checkCartStatus(cartId, unpaidCart);
        System.out.println("Step 5 - Website status for unpaid cart:");
        System.out.println(unpaidWebsiteStatus);

        // Step 6: Simulate a cart with missing customer details
        CustomerCart incompleteCart = new CustomerCart(cartId);
        incompleteCart.setPaid(true); // Paid but missing details

        Website incompleteWebsiteStatus = repository.checkCartStatus(cartId, incompleteCart);
        System.out.println("Step 6 - Website status for incomplete cart:");
        System.out.println(incompleteWebsiteStatus); */
    }
}
