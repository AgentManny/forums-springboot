package gg.manny.forums.rank;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankRepository extends MongoRepository<Rank, String> {

    /**
     * Fetches a role by it's name or identification    
     * @param name Name of the role
     *
     * @return Role that matches the criteria name
     */
    Rank findByName(String name);

    @Override
    Optional<Rank> findById(String id);
}
