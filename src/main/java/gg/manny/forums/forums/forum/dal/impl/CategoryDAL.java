package gg.manny.forums.forums.forum.dal.impl;

import gg.manny.forums.forums.forum.ForumCategory;
import gg.manny.forums.forums.forum.ForumThread;
import gg.manny.forums.forums.forum.dal.ICategoryDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Repository
public class CategoryDAL implements ICategoryDAL {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ForumCategory> findAll() {
        return mongoTemplate.findAll(ForumCategory.class);
    }

    @Override
    public ForumCategory getCategory(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, ForumCategory.class);
    }

    @Override
    public boolean updateCategory(String categoryName, ForumCategory newCategory) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(categoryName));

        ForumCategory category = mongoTemplate.findOne(query, ForumCategory.class);
        if (category != null && (newCategory.getName().equalsIgnoreCase(category.getName()) || getCategory(newCategory.getName()) != null)) {
            Update update = new Update();
            update.set("name", newCategory.getName());
            update.set("description", newCategory.getDescription());
            update.set("weight", newCategory.getWeight());
            update.set("permission", newCategory.getPermission());
            mongoTemplate.updateFirst(query, update, ForumCategory.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDisplayName(String categoryName, String displayName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(categoryName));

        ForumCategory category = mongoTemplate.findOne(query, ForumCategory.class);
        if (category != null) {
            Update update = new Update();
            update.set("name", displayName);
            mongoTemplate.updateFirst(query, update, ForumCategory.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDescription(String categoryName, String description) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(categoryName));

        ForumCategory category = mongoTemplate.findOne(query, ForumCategory.class);
        if (category != null) {
            Update update = new Update();
            update.set("description", description);
            mongoTemplate.updateFirst(query, update, ForumCategory.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateWeight(String categoryName, int weight) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(categoryName));

        ForumCategory category = mongoTemplate.findOne(query, ForumCategory.class);
        if (category != null) {
            Update update = new Update();
            update.set("weight", weight);
            mongoTemplate.updateFirst(query, update, ForumCategory.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePermission(String categoryName, String permission) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(categoryName));

        ForumCategory category = mongoTemplate.findOne(query, ForumCategory.class);
        if (category != null) {
            Update update = new Update();
            update.set("permission", permission);
            mongoTemplate.updateFirst(query, update, ForumCategory.class);
            return true;
        }
        return false;
    }

    @Override
    public ForumCategory addCategory(String name) {
        ForumCategory category = new ForumCategory(name);
        mongoTemplate.save(category);
        return category;
    }

    @Override
    public ForumCategory removeCategory(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findAndRemove(query, ForumCategory.class);
    }

    @Override
    public ForumThread addThread(ForumCategory category, ForumThread thread) {
        throw new NotImplementedException();
    }
}
