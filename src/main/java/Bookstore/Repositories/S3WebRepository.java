package Bookstore.Repositories;

import java.net.URI;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;


@Primary
@Repository
public class S3WebRepository implements IWebsiteRepository{
    final String BUCKET="lsit_bookstore";
    final String PREFIX="Website/";
    
    final String ENDPOINT_URL="https://storage.googleapis.com";

    S3Client s3client;
    AwsCredentials awsCredentials;
    ObjectMapper objectMapper;


    public S3WebRepository(){
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(ENDPOINT_URL))
            .region(Region.of("auto"))
            .build();

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Website checkCartStatus(UUID cartId, CustomerCart cart) {
        if(cart == null){
            return new Website(cartId){{
                setOrderStatus("FAILED - Cart not found.");
            }};
        }

        if(!cart.isPaid()){
            return new Website(cartId){{
                setOrderStatus("FAILED - Payment not completed.");
            }};
        }

        if(!cart.hasCustomerDetails()){
            return new Website(cartId){{
                setOrderStatus("FAILED - Customer details are incomplete.");
            }};
        }

        return getWebsite(cartId);
    }

    @Override
    public void updateWebsite(Website website) {
        saveWebsite(website);
    }

    private Website getWebsite(UUID cartId) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + cartId.toString())
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, Website.class);
        } catch (Exception e) {
            // Website does not exist, return a new one
            return new Website(cartId);
        }
    }

    // Save website details to S3
    private void saveWebsite(Website website) {
        try {
            String websiteJson = objectMapper.writeValueAsString(website);
            s3client.putObject(PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(PREFIX + website.getCartId().toString())
                    .build(), RequestBody.fromString(websiteJson));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing website with cart ID: " + website.getCartId(), e);
        }
    }

    
}
