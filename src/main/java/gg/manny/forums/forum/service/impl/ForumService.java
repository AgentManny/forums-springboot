package gg.manny.forums.forum.service.impl;

import gg.manny.forums.forum.Forum;
import gg.manny.forums.forum.service.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
    public Forum addForum(Forum forum) {
        return mongoTemplate.insert(forum);
    }
}
