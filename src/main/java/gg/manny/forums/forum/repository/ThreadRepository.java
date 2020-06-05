package gg.manny.forums.forum.repository;

import gg.manny.forums.forum.ForumThread;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThreadRepository extends MongoRepository<ForumThread, String> {
// We gotta save all threads so we can fetch instead of querying each created forum

    /** List all forum threads */
    List<ForumThread> findAll();

}
