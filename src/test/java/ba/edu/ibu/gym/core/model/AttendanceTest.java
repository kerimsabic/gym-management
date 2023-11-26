package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class AttendanceTest {
    @Test
    void shoudMarkNewAttendatce(){

        Member member1 = new Member();
        member1.setFirstName("Kerim");
        member1.setLastName("Sabic");
        member1.setId("someId");
        member1.setAddress("Sarajevo");
        member1.setPhone("11111");
        member1.setUserType(UserType.MEMBER);
        member1.setEmail("kerimsabic.com");

        Date date1= new Date();

        Attendance attendance= new Attendance(
                "someId",
                date1,
                member1

        );

        Assertions.assertEquals("someId",attendance.getId());
        Assertions.assertEquals("Kerim",attendance.getMember().getFirstName());
        Assertions.assertEquals("Sabic",attendance.getMember().getLastName());
        Assertions.assertEquals(UserType.MEMBER,attendance.getMember().getUserType());


    }
}
