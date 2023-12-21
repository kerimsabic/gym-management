package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TrainingPlanRepositoryTest {

    @Autowired
    private  TrainingPlanRepository trainingPlanRepository;


   /* public TrainingPlanRepositoryTest(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }*/

    @Test
    public void returnsAllTrainingPlans(){
        List<TrainingPlan> trainingPlans = trainingPlanRepository.findAll();
        Assertions.assertEquals(1,trainingPlans.size());
    }
}
