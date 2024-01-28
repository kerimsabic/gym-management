package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MembershipTest {

    @Test
    void shoudCreateNewMembership(){


        Date date1= new Date();
        Date date2= new Date();

        TrainingPlan trainingPlan= new TrainingPlan(
                "someId2",
                "testTrainingPlan",
                "created for testing purpose",
                "60$",
                StatusType.ONLINE
        );

        Member member1 = new Member();
        member1.setFirstName("Kerim");
        member1.setLastName("Sabic");
        member1.setId("someId");
        member1.setAddress("Sarajevo");
        member1.setPhone("11111");
        member1.setUserType(UserType.MEMBER);
        member1.setEmail("kerimsabic.com");





        Membership membership= new Membership(
                "someId",
                member1,
                date1,
                date2,
                trainingPlan,
                StatusType.ONLINE
        );



        Assertions.assertEquals("someId",membership.getId());
        Assertions.assertEquals("Kerim",membership.getMember().getFirstName());
        Assertions.assertEquals("Sabic",membership.getMember().getLastName());
        Assertions.assertEquals(UserType.MEMBER,membership.getMember().getUserType());
        Assertions.assertEquals("testTrainingPlan",membership.getTrainingPlan().getName());
        Assertions.assertEquals("someId2",membership.getTrainingPlan().getId());



    }
}
