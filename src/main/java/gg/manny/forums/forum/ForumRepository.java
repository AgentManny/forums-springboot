package gg.manny.forums.forum;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends MongoRepository<Forum, String> {

    /** List all forums */
    List<Forum> findAll();

    /** Retrieves a section by it's name, with it outputting */
    Forum getByName(String name);


}
