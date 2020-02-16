package gg.manny.forums.forums.forum.dal.impl;

import gg.manny.forums.forums.forum.ForumCategory;
import gg.manny.forums.forums.forum.ForumThread;
import gg.manny.forums.forums.forum.dal.ICategoryDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        query.addCriteria(Criteria.where("_id").is(name));
        return mongoTemplate.findOne(query, ForumCategory.class);
    }

    @Override
    public boolean updateCategory(String name, ForumCategory newCategory) {
        ForumCategory category = getCategory(name); // Should make sure it isn't null beforehand
        if (category != null) {
            category.setName(newCategory.getName());
            category.setDescription(newCategory.getDescription());
            category.setPermission(newCategory.getPermission());
            category.setWeight(newCategory.getWeight());
            mongoTemplate.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDisplayName(String categoryName, String displayName) {
        ForumCategory category = getCategory(categoryName);
        if (category != null) {
            category.setName(displayName);
            mongoTemplate.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDescription(String categoryName, String description) {
        ForumCategory category = getCategory(categoryName);
        if (category != null) {
            category.setDescription(description);
            mongoTemplate.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateWeight(String categoryName, int weight) {
        ForumCategory category = getCategory(categoryName);
        if (category != null) {
            category.setWeight(weight);
            mongoTemplate.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePermission(String categoryName, String permission) {
        ForumCategory category = getCategory(categoryName);
        if (category != null) {
            category.setPermission(permission);
            mongoTemplate.save(category);
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
        query.addCriteria(Criteria.where("_id").is(name));
        return mongoTemplate.findAndRemove(query, ForumCategory.class);
    }

    @Override
    public ForumThread addThread(ForumCategory category, ForumThread thread) {
        throw new NotImplementedException();
    }
}
