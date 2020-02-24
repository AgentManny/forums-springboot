package gg.manny.forums.role;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * Fetches a role by it's name or identification    
     * @param name Name of the role
     *
     * @return Role that matches the criteria name
     */
    Role findByName(String name);

    @Override
    Optional<Role> findById(String id);
}
