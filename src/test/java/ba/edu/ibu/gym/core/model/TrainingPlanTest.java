package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.StatusType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrainingPlanTest {

    @Test
    void shoudCreateNewTrainingPlan(){
        TrainingPlan trainingPlan= new TrainingPlan(
                "someId",
                "testTrainingPlan",
                "created for testing purpose",
                "60$",
                StatusType.ONLINE,
                "11:00-22:00",
                "1",
                true,
                true
        );

        Assertions.assertEquals("testTrainingPlan",trainingPlan.getName());
        Assertions.assertEquals("created for testing purpose",trainingPlan.getDescription());
        Assertions.assertEquals("60$",trainingPlan.getPrice());


    }
}
