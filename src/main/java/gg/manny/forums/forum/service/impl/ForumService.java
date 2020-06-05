package gg.manny.forums.forum.service.impl;

import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.ForumCategory;
import gg.manny.forums.forum.service.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumService implements IForumService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Forum> findAll() {
        return mongoTemplate.findAll(Forum.class);
    }

    @Override
    public Forum getForum(String name) {
        return mongoTemplate.findById(name, Forum.class);
    }

    @Override
    public List<ForumCategory> getSubForums() {
        List<ForumCategory> categories = new ArrayList<>();
        for (Forum forum : findAll()) {
            categories.addAll(forum.getCategories());
        }
        return categories;
    }


    @Override
    public Forum addForum(Forum forum) {
        mongoTemplate.save(forum);
        return forum;
    }
}
