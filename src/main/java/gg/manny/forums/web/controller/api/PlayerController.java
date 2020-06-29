package gg.manny.forums.web.controller.api;

import com.google.gson.JsonObject;
import gg.manny.forums.Application;
import gg.manny.forums.rank.RankRepository;
import gg.manny.forums.user.User;
import gg.manny.forums.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PlayerController {

    @Autowired private UserRepository userRepository;
    @Autowired private RankRepository rankRepository;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String loadProfile(@PathVariable UUID id) {
        JsonObject data = new JsonObject();

        User user = userRepository.findById(id).orElse(null);
        data.addProperty("success", user != null);

        JsonObject playerData = new JsonObject();
        if (user != null) {
            playerData.addProperty("id", id.toString());
            playerData.addProperty("username", user.getUsername());
        }

        data.add("player", playerData);
        return Application.GSON.toJson(data);
    }

}
