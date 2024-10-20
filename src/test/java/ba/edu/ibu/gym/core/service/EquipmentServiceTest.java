package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.EquipmentRepository;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import ba.edu.ibu.gym.rest.dto.EquipmentRequestDTO;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class EquipmentServiceTest {

    @MockBean
    EquipmentRepository equipmentRepository;

    @Autowired
    EquipmentService equipmentService;

    @Test
    public void shouldReturnEquipmentWhenCreated(){
        Equipment equipment= new Equipment();
        equipment.setManufacturer("Test123");
        equipment.setType("bench press");
        equipment.setId("testId");
        equipment.setName("someTest name");

        Mockito.when(equipmentRepository.save(ArgumentMatchers.any(Equipment.class))).thenReturn(equipment);

        Equipment savedEquipment= equipmentService.addEquipment(new EquipmentRequestDTO(equipment));
        Assertions.assertThat(equipment.getName()).isEqualTo(savedEquipment.getName());
        Assertions.assertThat(equipment.getType()).isEqualTo(savedEquipment.getType());
        Assertions.assertThat(equipment.getId()).isEqualTo(savedEquipment.getId());
    }

    @Test
    public void shouldReturnEquipmentById(){
        Equipment equipment= new Equipment();
        equipment.setManufacturer("Test123");
        equipment.setType("bench press");
        equipment.setId("testId");
        equipment.setName("someTest name");

        Mockito.when(equipmentRepository.findById("testId")).thenReturn(Optional.of(equipment));
        Equipment result= equipmentService.getEquipmentById(equipment.getId());
        Assertions.assertThat(equipment.getName()).isEqualTo(result.getName());
        Assertions.assertThat(equipment.getId()).isEqualTo(result.getId());
        Assertions.assertThat(equipment.getManufacturer()).isEqualTo(result.getManufacturer());
    }
}
