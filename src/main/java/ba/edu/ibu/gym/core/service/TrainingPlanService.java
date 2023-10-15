package ba.edu.ibu.gym.core.service;


import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanService {
    private final TrainingPlanRepository trainingPlanRepository;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }
    public List<TrainingPlan> findAll(){
        return trainingPlanRepository.findAll();
    }
    public TrainingPlan findById(int id){
        return trainingPlanRepository.findById(id);
    }
}
