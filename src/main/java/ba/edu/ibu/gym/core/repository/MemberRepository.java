package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member,String> {

}
