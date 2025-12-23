package sumdu.edu.ua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "sumdu.edu.ua")
public class AppInit {

    public static void main(String[] args) {
        SpringApplication.run(AppInit.class, args);
        System.out.println("\n=== Spring Boot Books Application Started ===");
        System.out.println("Application URL: http://localhost:8080/books");
        System.out.println("API URL: http://localhost:8080/api/books\n");
    }
}

