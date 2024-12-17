package Bookstore.Repositories;
import java.net.URI;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Bookstore.Models.CustomerCart;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Primary
@Repository
public class S3CustomerCartRepository implements ICustomerCartRepository{
    final String BUCKET="lsit_bookstore";
    final String PREFIX="CustomerCart/";
    
    
    final String ENDPOINT_URL="https://storage.googleapis.com";

    S3Client s3client;
    AwsCredentials awsCredentials;
    ObjectMapper objectMapper;


    public S3CustomerCartRepository(){
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(ENDPOINT_URL))
            .region(Region.of("auto"))
            .build();

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public CustomerCart createCart() {
        CustomerCart cart = new CustomerCart();
        addCart(cart);
        return cart;
    }

    @Override
    public CustomerCart getCart(UUID id) {
        
        try{
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build()
            ).readAllBytes();
            ObjectMapper om = new ObjectMapper();

            CustomerCart cart = om.readValue(objectBytes, CustomerCart.class);
            return cart;
        }
        catch(Exception e){
            System.out.println("Error fetching cart: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<CustomerCart> getAllCarts() {
        List<CustomerCart> carts = new ArrayList<>();

        List<S3Object> objects = s3client.listObjects(ListObjectsRequest.builder()
            .bucket(BUCKET)
            .prefix(PREFIX)
            .build()).contents();

        for (S3Object object : objects) {
            try {
                UUID cartId = UUID.fromString(object.key().substring(PREFIX.length()));
                CustomerCart cart = this.getCart(cartId);
                if (cart != null) {
                    carts.add(cart);
                }
            } catch (Exception e) {
            
            }
        }
        return carts;
    }

    @Override
    public void updateCart(CustomerCart cart) {
        if (!exists(cart.getId())) {
            throw new IllegalArgumentException("Book not found.");
        }
        addCart(cart);
    }

    public boolean exists(UUID id) {
        try {
            s3client.headObject(HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void removeCart(UUID id) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + id.toString())
                    .build());
        } catch (S3Exception e) {
            throw new RuntimeException("Error deleting cart with ID: " + id, e);
        }
    }

    public void addCart(CustomerCart cart){
        try{
            String cartJson = objectMapper.writeValueAsString(cart);

            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + cart.getId().toString())
                .build(),
                RequestBody.fromString(cartJson)
            );
        }
        catch(JsonProcessingException e){
            throw new RuntimeException("Error serializing CustomerCart with ID " + cart.getId(), e);
        }
    }
    
}
