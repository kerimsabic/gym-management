package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.service.TrainingPlanService;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trainingPlans")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<TrainingPlan>> getAllPlans() {
        return ResponseEntity.ok(trainingPlanService.getAllPlans());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<TrainingPlan> getPlanByID(@PathVariable String id) {
        return ResponseEntity.ok(trainingPlanService.getPlanById(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<TrainingPlan> createTrainingPlan(@RequestBody PlanRequestDTO plan) {
        return ResponseEntity.ok(trainingPlanService.createTrainingPlan(plan));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<TrainingPlan> updateTrainingPlan(@PathVariable String id, @RequestBody PlanRequestDTO plan){
        return ResponseEntity.ok(trainingPlanService.updateTrainigPlan(id,plan));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<Void> deleteTrainingPlan(@RequestParam String id){
        trainingPlanService.deleteTrainingPlan(id);
        return null;
    }


}
