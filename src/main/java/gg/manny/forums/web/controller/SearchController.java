package gg.manny.forums.web.controller;

import com.google.gson.JsonObject;
import gg.manny.forums.Application;
import gg.manny.forums.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/search-autocomplete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPlayerByName(@RequestParam String query) {
        JsonObject queryData = new JsonObject();

        userRepository.findByUsernameIgnoreCaseStartingWith(query, PageRequest.of(0, 5)).forEach(user -> {
            queryData.addProperty(user.getUsername(), user.getId().toString());
        });

        return Application.GSON.toJson(queryData);
    }
}