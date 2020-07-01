package gg.manny.forums.web.controller;

import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.repository.CategoryRepository;
import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.forum.service.impl.ForumService;
import gg.manny.forums.user.User;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForumController {

    public static boolean FORUMS_DISABLED = true; // TEMP
    public static String FORUM_DISABLED_MESSAGE = "Forums are currently disabled. Please check back later.";

    @Autowired
    private UserService userService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @RequestMapping(value = "/forums/{id}", method = RequestMethod.GET)
    public ModelAndView forumCategory(@PathVariable String id, HttpServletRequest request) {

        if ((User) request.getSession().getAttribute("user") == null && FORUMS_DISABLED) throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORUM_DISABLED_MESSAGE);
        ModelAndView modelAndView = new ModelAndView("forums/forum");
        ForumCategory subForum = categoryRepository.findById(id).orElse(null);
        if (subForum != null) {
            modelAndView.addObject("forum", subForum);
            modelAndView.addObject("threads", subForum.getThreads());
            return modelAndView;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Category not found"
        );
    }

    @RequestMapping(value = "/forums", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) {
        if ((User) request.getSession().getAttribute("user") == null && FORUMS_DISABLED) throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORUM_DISABLED_MESSAGE);
        ModelAndView modelAndView = new ModelAndView("forums");
        modelAndView.addObject("forums", forumRepository.findAll());
        return modelAndView;
    }

}