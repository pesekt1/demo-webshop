package swc3.demowebshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class DemoWebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebshopApplication.class, args);
    }

}
