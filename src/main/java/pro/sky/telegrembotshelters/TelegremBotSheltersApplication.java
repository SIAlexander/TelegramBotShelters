package pro.sky.telegrembotshelters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegremBotSheltersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegremBotSheltersApplication.class, args);
    }

}
