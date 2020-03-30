package gg.manny.forums.web.controller;

import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.ForumRepository;
import gg.manny.forums.forum.service.impl.ForumService;
import gg.manny.forums.user.User;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ForumController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumService forumService;


    @RequestMapping(value = "/forums", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forums");
        return modelAndView;
    }


    @RequestMapping(value = "/forums/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forums/create");
        return modelAndView;
    }

    @RequestMapping(value = "/forums/create/add", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Forum forum, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Forum forumExists = forumService.getForum(forum.getName());
        if (forumExists == null) {
            bindingResult
                    .rejectValue("name", "error.exists",
                            "Forum already exists");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/forums/create");
        } else {
            forumService.addForum(forum);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.setViewName("forums");
        }
        return modelAndView;
    }

}