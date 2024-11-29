package Bookstore.Repositories;


import java.net.URI;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Bookstore.Models.Book;
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
public class S3BookRepository implements IBookRepository {
    final String BUCKET="lsit_bookstore";
    final String PREFIX="Books/";
    
    final String ENDPOINT_URL="https://storage.googleapis.com";

    S3Client s3client;
    AwsCredentials awsCredentials;


    public S3BookRepository(){
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(ENDPOINT_URL))
            .region(Region.of("auto"))
            .build();
    }

    @Override
    public boolean exists(UUID id){
        try {
            s3client.headObject(HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build());
            return true;
        } catch (S3Exception e) {
            return false;
        }
        
    }

    public List<Book> list(){
        List<Book> books = new ArrayList<Book>();
        List<S3Object> objects = s3client.listObjects(ListObjectsRequest.builder()
          .bucket(BUCKET)
          .prefix(PREFIX)
          .build()  
        ).contents();

        for(S3Object o : objects){
            Book book = new Book();
            //book = this.get(UUID.fromString(o.key().substring(PREFIX.length())));
            book.setId(UUID.fromString(o.key().substring(PREFIX.length())));
            
            books.add(book);
        }

        return books;

    }

    public Book get(UUID id){
        try{
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build()
            ).readAllBytes();

            ObjectMapper om = new ObjectMapper();
            Book book = om.readValue(objectBytes, Book.class);

            return book;
        }catch(Exception e){
            return null;
        }

    }

    public void add(Book book){
        try{
            //book.setId(UUID.randomUUID());

            ObjectMapper om = new ObjectMapper();

            String bookJson = om.writeValueAsString(book);
            
            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + book.getId().toString())
                .build(),
                RequestBody.fromString(bookJson)
            );
        }
        catch(JsonProcessingException e){}
    }

    public void update(Book book){
        if (!exists(book.getId())) {
            throw new IllegalArgumentException("Book not found.");
        }
        add(book);

    }

    public void updateQuantity(UUID id, int quantity){
        Book book = this.get(id);
        if (book != null) {
            book.setQuantity(quantity);  
            this.update(book);  
        }
    }

    public void remove(UUID id){
        s3client.deleteObject(DeleteObjectRequest.builder()
            .bucket(BUCKET)
            .key(PREFIX + id.toString())
            .build()
        ); 

    }


}