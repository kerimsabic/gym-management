package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends MongoRepository<Membership, String> {
    Optional<Membership> findMembershipByMember_Id(String memberId);
}
