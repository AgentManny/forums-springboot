package gg.manny.forums.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByUsernameIgnoreCaseStartingWith(String username, Pageable pageable);

}
