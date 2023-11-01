package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends MongoRepository<Membership, String> {
}
