package ba.edu.ibu.gym.core.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class EquipmentTest {
    @Test
    void shoudCreateEquipmentItem(){
        Date date= new Date();
        ArrayList<Date>dates= new ArrayList<Date>();
        dates.add(date);
        Equipment equipment= new Equipment(
                "someId",
                "Bench",
                "BenchPress",
                "AllIn",
                "",
                dates

        );

        Assertions.assertEquals("someId",equipment.getId());
        Assertions.assertEquals("Bench",equipment.getName());
        Assertions.assertEquals("BenchPress",equipment.getType());
        Assertions.assertEquals("AllIn", equipment.getManufacturer());


    }
}
