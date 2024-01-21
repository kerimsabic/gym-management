package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EquipmentRepositoryTest {
    @Autowired
    private  EquipmentRepository equipmentRepository;


    @Test
    public void returnsAllEquipment(){
        List<Equipment> allEquipment = equipmentRepository.findAll();
        Assertions.assertEquals(4,allEquipment.size());
    }
}
