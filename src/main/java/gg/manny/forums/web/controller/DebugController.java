package gg.manny.forums.web.controller;

import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.forum.service.impl.ForumService;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DebugController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public UserDetails home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder builder = new StringBuilder();
        if (auth != null) {
            builder.append("AUTH EXISTS\n");
            if (auth.getDetails() != null) {
                builder.append("DETAILS EXISTS -- \n");
                return (UserDetails) auth.getPrincipal();
            }
        }
        return null;
    }

}