package gg.manny.forums.forum.repository;

import gg.manny.forums.forum.ForumCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<ForumCategory, String> {

    /** List all forum threads */
    List<ForumCategory> findAll();

    ForumCategory findByDisplayName(String displayName);
}
