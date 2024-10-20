package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //no need for now


    Optional<User> findByEmail(String email);
    @Query(value="{$or:[{email:'?0'}, {username:'?0'}]}")
    Optional<User> findByUsernameOrEmail(String username);


    List<User> findByUserType(UserType userType);



    List<User> findByStatusTypeAndUserType(StatusType statusType, UserType userType);



}
