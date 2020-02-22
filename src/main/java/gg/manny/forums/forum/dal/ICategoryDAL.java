package gg.manny.forums.forum.dal;

import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.ForumThread;

import java.util.List;

public interface ICategoryDAL {

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
     * Add a new category by a display name which automatically
     * converts into a unique identifier (replaces the spaces to dashes and is lowercase)
     *
     * @param name Name of the new category
     * @return Category created
     */
    ForumCategory addCategory(String name);

    /**
     * Remove a category by it's identifier
     *
     * @param name Unique identifier
     * @return Category that was removed
     */
    ForumCategory removeCategory(String name);

    ForumThread addThread(ForumCategory category, ForumThread thread);

}
