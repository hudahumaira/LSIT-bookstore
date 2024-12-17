package Bookstore.Repositories;

import java.util.UUID;

import Bookstore.Models.Delivery;

import java.net.URI;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
public class S3DeliveryRepository implements IDeliveryRepository{
    final String BUCKET="lsit_bookstore";
    final String PREFIX="Delivery/";
    
    final String ENDPOINT_URL="https://storage.googleapis.com";

    S3Client s3client;
    AwsCredentials awsCredentials;
    ObjectMapper objectMapper;


    public S3DeliveryRepository(){
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(ENDPOINT_URL))
            .region(Region.of("auto"))
            .build();

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Delivery createDelivery(UUID cartId) {
        Delivery delivery = new Delivery(cartId);
        saveDelivery(delivery);
        return delivery;
    }

    @Override
    public Delivery getDelivery(UUID cartId) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + cartId.toString())
                    .build()).readAllBytes();
            System.out.println("Hello");
            return objectMapper.readValue(objectBytes, Delivery.class);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public void updateDelivery(Delivery delivery) {
        if (!exists(delivery.getCartId())) {
            throw new IllegalArgumentException("Delivery not found for cart ID: " + delivery.getCartId());
        }
        saveDelivery(delivery);
    }

    @Override
    public void removeDelivery(UUID cartId) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + cartId.toString())
                    .build());
        } catch (S3Exception e) {
            throw new RuntimeException("Error deleting delivery for cart ID: " + cartId, e);
        }
    }

    private boolean exists(UUID cartId) {
        try {
            s3client.headObject(HeadObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + cartId.toString())
                    .build());
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    // Save a delivery to S3
    private void saveDelivery(Delivery delivery) {
        try {
            String deliveryJson = objectMapper.writeValueAsString(delivery);
            s3client.putObject(PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + delivery.getCartId().toString())
                    .build(), RequestBody.fromString(deliveryJson));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing delivery with cart ID: " + delivery.getCartId(), e);
        }
    }
    
}
