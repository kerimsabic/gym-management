package ba.edu.ibu.gym.core.repository;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.rest.dto.MemberDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface MemberRepository extends MongoRepository<Member,String> {
    //Page<Member> findAll(Pageable pageable);
    List<Member> findByStatusType(StatusType statusType);
}
