package gg.manny.forums.forum.repository;

import gg.manny.forums.forum.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumRepository extends MongoRepository<Forum, String> {

    /** Retrieves a section by it's name, with it outputting */
    Forum getByName(String name);



}
