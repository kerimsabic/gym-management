package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.repository.EquipmentRepository;
import ba.edu.ibu.gym.rest.dto.EquipmentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        if(equipment.getServiceHistory()==null){
            Date currentDate = new Date();
            equipment.setServiceHistory(new ArrayList<Date>());
            equipment.getServiceHistory().add(currentDate);
        }
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

    public Equipment serviceEquipment(String id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        if (equipment.isEmpty()) {
            throw new ResourceNotFoundException("Equipment with the given ID does not exist.");
        }

        Date currentDate = new Date();
        List<Date>serviceHistory;
        serviceHistory=equipment.get().getServiceHistory();
        serviceHistory.add(currentDate);
        equipment.get().setServiceHistory(serviceHistory);

        Equipment updatedEquipment = equipment.get();
         updatedEquipment.setId(equipment.get().getId());

        equipmentRepository.save(updatedEquipment);

        return updatedEquipment;
    }

    public void deleteEquipment(String id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        equipment.ifPresent(equipmentRepository::delete);

    }

}
