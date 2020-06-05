package gg.manny.forums.forum.controller;

import gg.manny.forums.Application;
import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.ForumThread;
import gg.manny.forums.forum.repository.CategoryRepository;
import gg.manny.forums.forum.repository.ForumRepository;
import gg.manny.forums.forum.repository.ThreadRepository;
import gg.manny.forums.forum.service.ICategoryService;
import gg.manny.forums.user.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/forum/category")
@AllArgsConstructor
public class CategoryController {

    /** Shows how many category threads are visible per page */
    public static final int THREADS_PER_PAGE = 10;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private ICategoryService categoryDAL;
    @Autowired private ForumRepository forumServie;
    @Autowired private ThreadRepository threadRepository;
    @Autowired private CategoryRepository categoryRepository;


    // todo remove this debug

    @RequestMapping(value = "/debug/fill", method = RequestMethod.GET)
    public ForumCategory fillDebug() {
        Forum mainForum = new Forum();
        mainForum.setName("The Craft Network");
        mainForum.setWeight(99);

        ForumCategory announcements = new ForumCategory("announcements", "Announcements", mainForum);
        announcements.setWeight(99);
        announcements.setDescription("News, announcements, changes, and more");

        mainForum.getCategories().add(announcements);
        categoryRepository.save(announcements);
     //   createThread(announcements, UUID.fromString("2f6f44cf-19a2-442a-944b-ede88be55651"), "This is a title", "**Hello this is manny** Okay example yes big woop __Yes__");

        forumServie.save(mainForum);
        return announcements;
    }

    public void createThread(ForumCategory forum, User author, String title, String content) {
        int id = Application.RANDOM.nextInt(900) + 100;
        while (!forum.getThreads().isEmpty() && forum.getThreads().contains(String.valueOf(id))) {
            id++;
        }

        ForumThread thread = new ForumThread(String.valueOf(id), author, title);
        thread.setBody(content);


        forum.getThreads().add(thread);
        threadRepository.save(thread); // Adds the thread to global thread collection

        createReply(thread, author, "This is a reply");

        System.out.println("Created thread " + id);
    }

    public void createReply(ForumThread thread, User author, String content) {
        AtomicInteger id = new AtomicInteger(Application.RANDOM.nextInt(900) + 100);
        thread.getReplies().forEach(reply -> {
            if (Integer.parseInt(reply.getId()) == id.get()) {
                id.getAndIncrement();
            }
        });

        ForumThread reply = new ForumThread(String.valueOf(id.get()), author);
        reply.setBody(content);

        thread.getReplies().add(reply);
        System.out.println("Added reply " + id + " for " + thread.getId());
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<ForumCategory> getCategories() {
        return categoryDAL.findAll();
    }

    @RequestMapping(value = "/{category}", method = RequestMethod.GET)
    public ForumCategory getCategory(@PathVariable String category) {
        return categoryDAL.getCategory(category);
    }

    @RequestMapping(value = "/{category}/update", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCategory(@PathVariable String category, @RequestBody ForumCategory updatedCategory) {
        LOGGER.info("Category " + category + " was updated");
        return categoryDAL.updateCategory(category, updatedCategory);
    }

    @RequestMapping(value = "/{category}/update/name", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    public boolean updateDisplayName(@PathVariable String category, @RequestBody String name) {
        LOGGER.info("Category " + category + " display name was set to " + name + ".");
        return categoryDAL.updateDisplayName(category, name);
    }

    @RequestMapping(value = "/{category}/update/description", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    public boolean updateDescription(@PathVariable String category, @RequestBody String description) {
        LOGGER.info("Category " + category + " description was set to " + description + ".");
        return categoryDAL.updateDescription(category, description);
    }

    @RequestMapping(value = "/{category}/update/permission", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    public boolean updatePermission(@PathVariable String category, @RequestBody String permission) {
        LOGGER.info("Category " + category + " permission was set to " + permission + ".");
        return categoryDAL.updatePermission(category, permission);
    }

    @RequestMapping(value = "/{category}/update/weight", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    public boolean updateWeight(@PathVariable String category, @RequestBody int weight) {
        LOGGER.info("Category " + category + " weight was set to " + weight + ".");
        return categoryDAL.updateWeight(category, weight);
    }

    @RequestMapping(value = "/add/{category}", method = RequestMethod.GET)
    public ForumCategory addCategory(@PathVariable String category) {
        return null;
    }

    @RequestMapping(value = "/remove/{category}", method = RequestMethod.GET)
    public ForumCategory removeCategory(@PathVariable String category) {
        return categoryDAL.removeCategory(category);
    }

}
