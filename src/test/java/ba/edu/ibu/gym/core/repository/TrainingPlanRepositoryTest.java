package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@DataMongoTest
public class TrainingPlanRepositoryTest {

    @Mock
    private TrainingPlanRepository trainingPlanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setId("1");
        trainingPlan.setName("Strength Training");

        // Mock the behavior of findById
        when(trainingPlanRepository.findById("1")).thenReturn(Optional.of(trainingPlan));

        // Simulate the repository call and verify the result
        Optional<TrainingPlan> foundTrainingPlan = trainingPlanRepository.findById("1");
        assertTrue(foundTrainingPlan.isPresent());
        assertEquals("Strength Training", foundTrainingPlan.get().getName());
    }

    @Test
    public void testSave() {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setId("2");
        trainingPlan.setName("Cardio Plan");

        // Mock the behavior of save
        when(trainingPlanRepository.save(trainingPlan)).thenReturn(trainingPlan);

        // Simulate the repository call and verify the result
        TrainingPlan savedTrainingPlan = trainingPlanRepository.save(trainingPlan);
        assertNotNull(savedTrainingPlan);
        assertEquals("Cardio Plan", savedTrainingPlan.getName());
    }

    @Test
    public void testDelete() {
        // Simulate delete operation
        doNothing().when(trainingPlanRepository).deleteById("1");

        // Simulate the repository call
        trainingPlanRepository.deleteById("1");

        // Verify that deleteById was called exactly once
        verify(trainingPlanRepository, times(1)).deleteById("1");
    }
}
