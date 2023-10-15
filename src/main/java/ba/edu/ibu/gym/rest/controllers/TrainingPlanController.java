package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.service.TrainingPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/trainingPlans")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }
    @GetMapping
    public List<TrainingPlan> findAll(){
        return trainingPlanService.findAll();
    }
    @GetMapping("/{id}")
    public TrainingPlan findById(@PathVariable int id){
        return trainingPlanService.findById(id);
    }
}
