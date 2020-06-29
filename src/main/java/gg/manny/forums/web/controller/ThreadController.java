package gg.manny.forums.web.controller;

import gg.manny.forums.Application;
import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.ForumThread;
import gg.manny.forums.forum.repository.CategoryRepository;
import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.forum.service.impl.ForumService;
import gg.manny.forums.user.User;
import gg.manny.forums.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ThreadController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //
    // Get thread details
    //

    @RequestMapping(value = "/thread/{id}/{title}", method = RequestMethod.GET)
    public ModelAndView thread(@PathVariable String id, @PathVariable(required = false) String title) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<ForumThread> forumThread = threadRepository.findById(id);
        if (forumThread.isPresent()) {
            ForumThread thread = forumThread.get();
            modelAndView.setViewName("forums/thread");

            modelAndView.addObject("forum", thread.getForum());
            modelAndView.addObject("thread", thread);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Thread not found"
            );
        }
        return modelAndView;
    }

    @RequestMapping(value = "/thread/{id}", method = RequestMethod.GET)
    public ModelAndView thread(@PathVariable String id) {
        return thread(id, ""); // Title is optional
    }

    //
    // Edit section
    //

    @RequestMapping(value = "/thread/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editThread(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forums/edit");
        Optional<ForumThread> thread = threadRepository.findById(id);
        if (thread.isPresent()) {         // todo Make sure they have permission to edit this thread
            modelAndView.addObject("thread", thread);
            return modelAndView;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Thread not found"
        );
    }

    @RequestMapping(value = "/thread/edit/{id}", method = RequestMethod.POST)
    public ModelAndView createThread(@PathVariable String id, @Valid @RequestBody String body, BindingResult bindingResult) {
        Optional<ForumThread> forumThread = threadRepository.findById(id);
        if (forumThread.isPresent()) {         // todo Make sure they have permission to edit this thread - e.g. if they own it
            ForumThread thread = forumThread.get();
            thread.setBody(body);
            thread.setLastEdited(System.currentTimeMillis());
            thread.setLastEditedBy(UUID.fromString("ae171c06-36a8-4a9c-824b-393932b6a1b3")); // debug todo set to user -- also make sure they're logging in LOL
            threadRepository.save(thread);
            return new ModelAndView("redirect:" + thread.getFriendlyUrl())
                    .addObject("edit", "success"); // Notify that they've edited successfully
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Thread not found"
        );
    }

    //
    // Create section
    //

    @RequestMapping(value = "/thread/create", method = RequestMethod.GET)
    public ModelAndView createThread(HttpServletRequest request) {
        return createThread("", request); // Redirects to empty forum
    }

    @RequestMapping(value = "/thread/create/{id}", method = RequestMethod.GET)
    public ModelAndView createThread(@PathVariable String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forums/new");

        if (!id.isEmpty() && !categoryRepository.findById(id).isPresent()) { // Prevent trying to autofill a category that doesn't exist
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found"
            );
        }


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "User not logged in" // todo We should also re-direct to /login/
            );
        }

        modelAndView.addObject("id", id);
        modelAndView.addObject("forums", forumService.getSubForums());

        // todo make sure they have access to the forum they are trying to post to
        return modelAndView;
    }

    @RequestMapping(value = "/thread/create", method = RequestMethod.POST)
    public ModelAndView createThread(@Valid ForumThread thread, BindingResult bindingResult, HttpServletRequest request) {
        // todo make sure they have access to the forum they are trying to post to
        ForumCategory subForum = categoryRepository.findByDisplayName(thread.getForum());
        if (subForum == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found"
            );
        }
        thread.setCategory(subForum);

        Forum forum = subForum.getForum();
        // if(forum.hasAccess(user) && subForum.hasAccess(user)) // make sure it just sends an error saying u cant go there buddy!

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "User not logged in" // todo We should also re-direct to /login/
            );
        }

        thread.setId(getThreadId());
        thread.setAuthor(user); // debug todo set to user -- also make sure they're logging in LOL
        subForum.getThreads().add(thread); // only stores id now
        subForum.setLastActivity(System.currentTimeMillis());
        threadRepository.save(thread);
        categoryRepository.save(subForum);
        forumRepository.save(forum);
        System.out.println("Saved and updated.");

        // Redirect them to there new thread :D
        return new ModelAndView("redirect:" + thread.getFriendlyUrl());
    }

    /**
     * Returns unique thread id for a forum thread
     * @return Thread identifier
     */
    public String getThreadId() {
        int threadId = Application.RANDOM.nextInt(900) + 100; // Gotta make sure there is no thread id called that or we'll be in some issues.
        while (threadRepository.findById(String.valueOf(threadId)).isPresent()) {
            threadId++;
        }
        return String.valueOf(threadId);
    }


}