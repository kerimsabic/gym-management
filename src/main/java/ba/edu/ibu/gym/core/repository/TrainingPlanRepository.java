package ba.edu.ibu.gym.core.repository;


import ba.edu.ibu.gym.core.model.TrainingPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TrainingPlanRepository extends MongoRepository<TrainingPlan, String> {


}
