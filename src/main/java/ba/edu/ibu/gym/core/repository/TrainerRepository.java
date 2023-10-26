package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends MongoRepository<Trainer,String> {

}
