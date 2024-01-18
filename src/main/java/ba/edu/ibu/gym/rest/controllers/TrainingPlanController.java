package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.service.TrainingPlanService;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trainingPlans")
@SecurityRequirement(name = "JWT Security")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<TrainingPlan>> getAllPlans() {
        return ResponseEntity.ok(trainingPlanService.getAllPlans());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TrainingPlan> getPlanByID(@PathVariable String id) {
        return ResponseEntity.ok(trainingPlanService.getPlanById(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TrainingPlan> createTrainingPlan(@RequestBody PlanRequestDTO plan) {
        return ResponseEntity.ok(trainingPlanService.createTrainingPlan(plan));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TrainingPlan> updateTrainingPlan(@PathVariable String id, @RequestBody PlanRequestDTO plan){
        return ResponseEntity.ok(trainingPlanService.updateTrainigPlan(id,plan));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteTrainingPlan(@PathVariable String id){
        trainingPlanService.deleteTrainingPlan(id);
        return null;
    }


}
