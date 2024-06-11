package ba.edu.ibu.gym.core.repository;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.rest.dto.MemberDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends MongoRepository<Member,String> {
   // @Query("{'firstName': { $regex: ., $options:'i' }}")
    Page<Member> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String surname,Pageable pageable);
    List<Member> findByStatusType(StatusType statusType);
    List<Member> findByFirstName(String name);

    Optional<Member> findByEmail(String email);

    List<Member> findByStatusTypeAndUserType(StatusType statusType, UserType userType);

    Page<Member> findByFirstNameContaining(String keyword,Pageable pageable);

}
