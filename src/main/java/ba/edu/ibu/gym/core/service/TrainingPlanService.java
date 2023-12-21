package ba.edu.ibu.gym.core.service;


import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingPlanService {
    private TrainingPlanRepository trainingPlanRepository;



    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    public List<TrainingPlan> getAllPlans() {
        return trainingPlanRepository.findAll();
    }

    public TrainingPlan getPlanById(String id) {
        Optional<TrainingPlan> trainingPlan = trainingPlanRepository.findById(id);
        if (trainingPlan.isEmpty()) {
            throw new ResourceNotFoundException("Training plan with the given ID does not exist.");
        }
        return trainingPlan.get();
    }

    public TrainingPlan createTrainingPlan(PlanRequestDTO plan) {
        TrainingPlan trainingPlan = trainingPlanRepository.save(plan.toEntity());
        return trainingPlan;
    }

    public TrainingPlan updateTrainigPlan(String id, PlanRequestDTO plan) {
        Optional<TrainingPlan> trainingPlan = trainingPlanRepository.findById(id);
        if (trainingPlan.isEmpty()) {
            throw new ResourceNotFoundException("Training plan with the given ID does not exist.");
        }
        TrainingPlan updatedPlan = plan.toEntity();
        updatedPlan.setId(trainingPlan.get().getId());
        updatedPlan = trainingPlanRepository.save(updatedPlan);
        return updatedPlan;
    }

    public void deleteTrainingPlan(String id) {
        //TrainingPlan trainingPlan=getPlanById(id);
        //trainingPlanRepository.delete(trainingPlan);
        Optional<TrainingPlan> trainingPlan = trainingPlanRepository.findById(id);
        trainingPlan.ifPresent(trainingPlanRepository::delete);

    }
}
