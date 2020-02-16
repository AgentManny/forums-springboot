package gg.manny.forums;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@Getter
@SpringBootApplication
public class Application {

    public static final Random RANDOM = new Random();

    @Getter private static Application instance;

    public Application() {
    }

    public static void main(String[] args) {
        instance = new Application();

        SpringApplication.run(Application.class, args);
    }

}
