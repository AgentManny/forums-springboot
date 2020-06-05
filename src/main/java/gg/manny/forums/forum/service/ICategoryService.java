package gg.manny.forums.forum.service;

import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.ForumThread;

import java.util.List;

public interface ICategoryService {

    /** List all categories */
    List<ForumCategory> findAll();

    /** Retrieves a category by it's name, with it outputting */
    ForumCategory getCategory(String name);

    /**
     * Updates a category data for mass changes, it
     * supports modifying description, weight, display name
     *
     * @return Category updated
     */
    boolean updateCategory(String name, ForumCategory newCategory);

    /** Returns updated display name of a category */
    boolean updateDisplayName(String category, String name);

    /** Returns updated description of a category */
    boolean updateDescription(String category, String description);

    /** Returns updated weight of a category */
    boolean updateWeight(String category, int weight);

    /** Returns updated permission of a category */
    boolean updatePermission(String category, String permission);

    /**
     * Remove a category by it's identifier
     *
     * @param name Unique identifier
     * @return Category that was removed
     */
    ForumCategory removeCategory(String name);

    ForumThread addThread(ForumCategory category, ForumThread thread);

}
