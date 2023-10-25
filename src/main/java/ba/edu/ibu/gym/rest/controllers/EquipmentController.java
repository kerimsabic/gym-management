package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.service.EquipmentService;
import ba.edu.ibu.gym.rest.dto.EquipmentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable String id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<Equipment> addEquipment(@RequestBody EquipmentRequestDTO equipment) {
        return ResponseEntity.ok(equipmentService.addEquipment(equipment));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable String id, @RequestBody  EquipmentRequestDTO equipment){
        return ResponseEntity.ok(equipmentService.updateEquipment(id,equipment));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<Void> deleteEquipment(@RequestParam String id){
        equipmentService.deleteEquipment(id);
        return null;
    }

}
