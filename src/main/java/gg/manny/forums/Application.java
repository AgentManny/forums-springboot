package gg.manny.forums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.manny.forums.rank.Rank;
import gg.manny.forums.rank.RankRepository;
import lombok.Getter;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@Getter
@SpringBootApplication
public class Application {

    public static final Random RANDOM = new Random();

    public static final Parser MARKDOWN_PARSER = Parser.builder().build();
    public static final HtmlRenderer MARKDOWN_RENDERER = HtmlRenderer.builder().build();

    public static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    public static final Gson PLAIN_GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    @Getter private static Application instance;

    @Value("api.key")
    @Getter private static String apiKey;

    public Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        instance = new Application();
    }

    public static void debug(String title, String... messages) {
        System.out.println(" - - - - - - - DEBUGGING: " + title + " - - - - - - - - - ");
        for (String message : messages) {
            System.out.println(" - -> " + message);
        }
        System.out.println(" - - - - - - - - - END OF DEBUG - - - - - - - - - - - ");
    }

    @Bean
    CommandLineRunner init(RankRepository roleRepository) {

        return args -> {

            if (!roleRepository.findById("default").isPresent()) {
                Rank userRole = new Rank();
                userRole.setId("default");
                userRole.setName("Default");
                userRole.setColor("#ffff");
                userRole.setWeight(-1);
                roleRepository.save(userRole);
            }

            if (!roleRepository.findById("admin").isPresent()) {
                Rank adminRole = new Rank();
                adminRole.setId("admin");
                adminRole.setName("Admin");
                adminRole.setColor("#4444");
                adminRole.setWeight(Integer.MAX_VALUE);
                adminRole.addPermission("permission.dashboard");
                roleRepository.save(adminRole);
            }
        };

    }
}
