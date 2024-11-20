package Bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//Steps to run the application:
//Run the application by executing the Main class.
//Interact with the API via Swagger:
//Open the Swagger UI (http://localhost:8080/swagger-ui.html) in your browser.
//You will see a list of all your endpoints under the Book API tag.
//Click on each endpoint to view details, provide input (for POST or PUT), and execute them directly from the UI.


@SpringBootApplication
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
}
