package gg.manny.forums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.manny.forums.role.Role;
import gg.manny.forums.role.RoleRepository;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@Getter
@SpringBootApplication
public class Application {

    public static final Random RANDOM = new Random();

    public static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    @Getter private static Application instance;

    public Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        instance = new Application();
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {
            if (!roleRepository.findById("default").isPresent()) {
                Role userRole = new Role();
                userRole.setId("default");
                userRole.setName("Default");
                userRole.setColor("#ffff");
                userRole.setWeight(-1);
                roleRepository.save(userRole);
            }

            if (!roleRepository.findById("admin").isPresent()) {
                Role adminRole = new Role();
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
