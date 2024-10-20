package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
public class EquipmentRepositoryTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndFindById() {
        // Create an Equipment instance
        Equipment equipment = new Equipment();
        equipment.setId("1");
        equipment.setName("Treadmill");


        // Mock the behavior of save and findById methods
        when(equipmentRepository.findById("1")).thenReturn(Optional.of(equipment));

        // Simulate the save operation
        equipmentRepository.save(equipment);

        // Verify that findById returns the expected equipment
        Optional<Equipment> foundEquipment = equipmentRepository.findById("1");
        assertTrue(foundEquipment.isPresent());
        assertEquals("Treadmill", foundEquipment.get().getName());

    }

    @Test
    public void testFindAll() {
        // Create multiple Equipment instances
        Equipment equipment1 = new Equipment();
        equipment1.setId("1");
        equipment1.setName("Treadmill");


        Equipment equipment2 = new Equipment();
        equipment2.setId("2");
        equipment2.setName("Dumbbell");



        when(equipmentRepository.findAll()).thenReturn(List.of(equipment1, equipment2));


        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertEquals(2, equipmentList.size());
        assertEquals("Treadmill", equipmentList.get(0).getName());
        assertEquals("Dumbbell", equipmentList.get(1).getName());
    }

    @Test
    public void testDeleteById() {
        // Create an Equipment instance
        Equipment equipment = new Equipment();
        equipment.setId("1");
        equipment.setName("Treadmill");


        // Mock the behavior of deleteById
        doNothing().when(equipmentRepository).deleteById("1");


        equipmentRepository.deleteById("1");


        verify(equipmentRepository).deleteById("1");
    }

    @Test
    public void testSaveAndFindByName() {

        Equipment equipment = new Equipment();
        equipment.setId("1");
        equipment.setName("Treadmill");

        when(equipmentRepository.findByName("Treadmill")).thenReturn(List.of(equipment));

        equipmentRepository.save(equipment);

        List<Equipment> foundEquipments = equipmentRepository.findByName("Treadmill");
        assertFalse(foundEquipments.isEmpty());
        assertEquals("Treadmill", foundEquipments.get(0).getName());
    }
}
