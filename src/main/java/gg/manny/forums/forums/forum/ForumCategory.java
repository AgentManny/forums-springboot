package gg.manny.forums.forums.forum;

import java.util.List;

public interface ForumCategory {

    /** Returns the category name */
    String name();

    /** Returns the description of a category */
    String description();

    /** List all threads included inside a category*/
    List<ForumThread> threads();

    /** Weight of the category which will be sorted on listing */
    default int weight() {
        return 0;
    }

    /**
     * Permission node required to view a categories' threads,
     * if empty, it'll be viewable by anyone including non-registered
     * users.
     */
    default String permission() {
        return "";
    }
}
