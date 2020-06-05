package gg.manny.forums.forum.service;

import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.ForumCategory;

import java.util.List;

public interface IForumService {

    /** List all forums */
    List<Forum> findAll();

    /** Retrieves a forum by it's name, with it outputting */
    Forum getForum(String name);

    List<ForumCategory> getSubForums();

    /**
     * Add a new forum by a display name which automatically
     * converts into a unique identifier (replaces the spaces to dashes and is lowercase)
     *
     * @param forum Forum to be added
     * @return Forum created
     */
    Forum addForum(Forum forum);

}
