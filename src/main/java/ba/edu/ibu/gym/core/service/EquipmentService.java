package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.repository.EquipmentRepository;
import ba.edu.ibu.gym.rest.dto.EquipmentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    public EquipmentRepository equipmentRepository;



    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> getAllEquipment(){
        return equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(String id){
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        if(equipment.isEmpty()){
            throw new ResourceNotFoundException("Equipment with the given ID does not exist.");
        }
        return equipment.get();
    }

    public Equipment addEquipment(EquipmentRequestDTO equipment){
        Equipment gymEquipment= equipmentRepository.save(equipment.toEntity());
        return gymEquipment;
    }

    public Equipment updateEquipment(String id, EquipmentRequestDTO equipmentRequestDTO) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        if (equipment.isEmpty()) {
            throw new ResourceNotFoundException("Equipment with the given ID does not exist.");
        }
        Equipment updatedEquipment = equipmentRequestDTO.toEntity();
        updatedEquipment.setId(equipment.get().getId());
        updatedEquipment = equipmentRepository.save(updatedEquipment);
        return updatedEquipment;
    }

    public void deleteEquipment(String id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        equipment.ifPresent(equipmentRepository::delete);

    }

}
