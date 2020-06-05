package gg.manny.forums.web.controller;

import gg.manny.forums.forum.repository.CategoryRepository;
import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        categoryRepository.findById("announcements").ifPresent(category ->
                modelAndView.addObject("announcements", category.getThreads())
        );

        modelAndView.setViewName("home");
        return modelAndView;
    }
}