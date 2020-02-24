package gg.manny.forums.forum;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends MongoRepository<ForumCategory, String> {

    /** List all categories */
    List<ForumCategory> findAll();

    /** Retrieves a category by it's name, with it outputting */
    ForumCategory getByName(String name);


}
