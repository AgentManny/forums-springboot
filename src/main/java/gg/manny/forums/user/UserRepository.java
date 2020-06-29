package gg.manny.forums.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    User findByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String username);

    User findByIdAndUsername(UUID id, String username);

    Optional<User> findByEmail(String email);

    List<User> findByUsernameIgnoreCaseStartingWith(String username, Pageable pageable);

}
