package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.service.TrainerService;
import ba.edu.ibu.gym.core.service.UserService;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import ba.edu.ibu.gym.rest.dto.TrainerRequestDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<TrainerDTO>> getTrainers() {
        return ResponseEntity.ok(trainerService.getTrainers());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable String id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }
    @RequestMapping(method = RequestMethod.POST,path = "/register")
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerRequestDTO trainer){
        return ResponseEntity.ok(trainerService.addTrainer(trainer));
    }
    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable String id,@RequestBody TrainerRequestDTO trainer){
        return ResponseEntity.ok(trainerService.updateTrainer(id,trainer));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable String id){
        trainerService.deleteTrainer(id);
        return null;
    }
}
