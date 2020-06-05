package gg.manny.forums.web.controller;

import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.forum.service.impl.ForumService;
import gg.manny.forums.user.User;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ThreadRepository threadRepository;

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public ModelAndView getPlayer(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("player");

        User user = userService.findUserByName(id);
        if (user == null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found"
        );

        modelAndView.addObject("user", user);
        return modelAndView;
    }
}