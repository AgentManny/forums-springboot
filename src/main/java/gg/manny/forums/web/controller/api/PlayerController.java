package gg.manny.forums.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import gg.manny.forums.Application;
import gg.manny.forums.rank.RankRepository;
import gg.manny.forums.user.User;
import gg.manny.forums.user.UserRepository;
import gg.manny.forums.util.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static gg.manny.forums.web.controller.api.RankController.AUTH_HEADER;

@RestController
public class PlayerController {

    @Autowired private UserRepository userRepository;
    @Autowired private RankRepository rankRepository;

    @RequestMapping(value = "/api/player", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String loadProfile(
            @RequestParam(required = false, defaultValue = "") String uuid,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String email,
            HttpServletRequest request) {
        String key = request.getHeader(AUTH_HEADER);
        if (key == null || !key.equals(Application.getApiKey())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided");
        }

        JsonObject data = new JsonObject();

        Optional<User> user;
        if (!uuid.isEmpty()) {
            UUID matchedId = UUIDs.parse(uuid);
            if (matchedId == null) {
                return generateCause("Malformed UUID");
            }

            user = userRepository.findById(matchedId);
        } else if (!name.isEmpty()) {
            user = userRepository.findByUsernameIgnoreCase(name);
        } else if (!email.isEmpty()) {
            user = userRepository.findByEmail(email);
        } else {
            return generateCause("No 'name' or 'uuid' or 'email' field");
        }

        data.addProperty("success", user.isPresent());

        JsonObject playerData;
        try {
            playerData = user.isPresent() ? Application.GSON.fromJson(new ObjectMapper().writeValueAsString(user.get()), JsonObject.class) : null;
            if (playerData != null) {
                if (email.isEmpty()) {
                    playerData.remove("email");
                }
                playerData.remove("password");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to deserialize player data of " + user.get().getUsername());
        }

        data.add("player", playerData);
        return Application.PLAIN_GSON.toJson(data);
    }

    public static String generateCause(String cause) {
        JsonObject data = new JsonObject();
        data.addProperty("success", false);
        if (cause != null) {
            data.addProperty("cause", cause);
        }
        return Application.PLAIN_GSON.toJson(data);
    }

}
