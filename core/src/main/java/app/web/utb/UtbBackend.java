package app.web.utb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UtbBackend {

    public static void main(String[] args) {
        SpringApplication.run(UtbBackend.class, args);
    }

}
