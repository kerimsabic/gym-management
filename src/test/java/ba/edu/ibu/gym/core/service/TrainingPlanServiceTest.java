package ba.edu.ibu.gym.core.service;


import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class TrainingPlanServiceTest {

    @MockBean
    TrainingPlanRepository trainingPlanRepository;

    @Autowired
    TrainingPlanService trainingPlanService;

    @Test
    public void shouldReturnTrainingPlanWhenCreated(){
        TrainingPlan trainingPlan= new TrainingPlan();
        trainingPlan.setName("Test training Plan");
        trainingPlan.setDescription("Created for purpose of testing");
        trainingPlan.setPrice("60$");

        Mockito.when(trainingPlanRepository.save(ArgumentMatchers.any(TrainingPlan.class))).thenReturn(trainingPlan);

        TrainingPlan savedTrainingPlan= trainingPlanService.createTrainingPlan(new PlanRequestDTO(trainingPlan));
        Assertions.assertThat(trainingPlan.getName()).isEqualTo(savedTrainingPlan.getName());
        Assertions.assertThat(trainingPlan.getDescription()).isEqualTo(savedTrainingPlan.getDescription());
        Assertions.assertThat(trainingPlan.getPrice()).isEqualTo(savedTrainingPlan.getPrice());
    }

    @Test
    public void shouldReturnTrainingPlanById(){
        TrainingPlan trainingPlan= new TrainingPlan();
        trainingPlan.setId("testId");
        trainingPlan.setName("Test training Plan");
        trainingPlan.setDescription("Created for purpose of testing");
        trainingPlan.setPrice("60$");

        Mockito.when(trainingPlanRepository.findById("testId")).thenReturn(Optional.of(trainingPlan));
        TrainingPlan result= trainingPlanService.getPlanById(trainingPlan.getId());
        Assertions.assertThat(trainingPlan.getName()).isEqualTo(result.getName());
        Assertions.assertThat(trainingPlan.getDescription()).isEqualTo(result.getDescription());
        Assertions.assertThat(trainingPlan.getPrice()).isEqualTo(result.getPrice());
    }
}
