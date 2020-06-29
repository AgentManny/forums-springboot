package gg.manny.forums.web.controller;

import gg.manny.forums.rank.Rank;
import gg.manny.forums.rank.RankRepository;
import gg.manny.forums.user.User;
import gg.manny.forums.user.grant.Grant;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RankRepository rankRepository;


    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public ModelAndView getPlayer(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("player");

        User user = userService.findUserByName(id);
        if (user == null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found"
        );

        modelAndView.addObject("page", "general");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/player/{id}/grants", method = RequestMethod.GET)
    public ModelAndView getPlayerGrants(@PathVariable String id) {
        ModelAndView modelAndView = getPlayer(id);
        modelAndView.addObject("page", "manage");
        modelAndView.addObject("section", "grants");
        return modelAndView;
    }

    @RequestMapping(value = "/player/{id}/grants/create", method = RequestMethod.GET)
    public ModelAndView getPlayerGrantsCreate(@PathVariable String id) {
        ModelAndView modelAndView = getPlayer(id);
        modelAndView.addObject("page", "manage");
        modelAndView.addObject("section", "grants_add");
        return modelAndView;
    }

    @RequestMapping(value = "/player/{id}/grants/create", method = RequestMethod.POST)
    public ModelAndView getGrants(@PathVariable String id, @Valid Grant grant) {
        User user = userService.findUserByName(id);
        if (user == null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found"
        );

        Optional<Rank> rank = rankRepository.findById(grant.getRankId());
        if (rank.isPresent()) {
            grant.setRank(rank.get());
            user.getGrants().add(grant);
            return new ModelAndView("redirect:/player/" + id + "/grants");
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rank not found"
        );
    }
}