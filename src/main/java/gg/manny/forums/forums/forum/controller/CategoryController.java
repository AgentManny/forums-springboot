package gg.manny.forums.forums.forum.controller;

import gg.manny.forums.forums.forum.ForumCategory;
import gg.manny.forums.forums.forum.dal.ICategoryDAL;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/category")
@AllArgsConstructor
public class CategoryController {

    /** Shows how many category threads are visible per page */
    public static final int THREADS_PER_PAGE = 10;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private ICategoryDAL categoryDAL;

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
        return categoryDAL.addCategory(category);
    }

    @RequestMapping(value = "/remove/{category}", method = RequestMethod.GET)
    public ForumCategory removeCategory(@PathVariable String category) {
        return categoryDAL.removeCategory(category);
    }

}
