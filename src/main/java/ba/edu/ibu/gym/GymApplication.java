package ba.edu.ibu.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
//@EnableMongoRepositories({"ba.edu.ibu.gym.core.repository"})
//@ComponentScan(basePackages = "ba.edu.ibu.gym.core.repository")

public class GymApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }

}
