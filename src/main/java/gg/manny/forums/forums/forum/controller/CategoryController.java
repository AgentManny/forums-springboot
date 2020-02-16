package gg.manny.forums.forums.forum.controller;

import gg.manny.forums.forums.forum.ForumCategory;
import gg.manny.forums.forums.forum.dal.ICategoryDAL;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public List<ForumCategory> getAllThreads() {
        return categoryDAL.findAll();
    }

    @RequestMapping(value = "/{category}", method = RequestMethod.GET)
    public ForumCategory getCategory(@PathVariable String category) {
        return categoryDAL.getCategory(category);
    }

    @RequestMapping(value = "/{category}/update", method = RequestMethod.GET)
    public boolean updateCategory(@PathVariable String category, @RequestBody ForumCategory updatedCategory) {
        return categoryDAL.updateCategory(category, updatedCategory);
    }

    @RequestMapping(value = "/{category}/update/name", method = RequestMethod.GET)
    public boolean updateDisplayName(@PathVariable String category, @RequestBody String displayName) {
        return categoryDAL.updateDisplayName(category, displayName);
    }

    @RequestMapping(value = "/{category}/update/description", method = RequestMethod.GET)
    public boolean updateDescription(@PathVariable String category, @RequestBody String description) {
        return categoryDAL.updateDescription(category, description);
    }

    @RequestMapping(value = "/{category}/update/permission", method = RequestMethod.GET)
    public boolean updatePermission(@PathVariable String category, @RequestBody String permission) {
        return categoryDAL.updatePermission(category, permission);
    }

    @RequestMapping(value = "/{category}/update/weight", method = RequestMethod.GET)
    public boolean updateWeight(@PathVariable String category, @RequestBody int weight) {
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
