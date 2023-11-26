package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.EquipmentRepository;
import ba.edu.ibu.gym.core.repository.MembershipRepository;
import ba.edu.ibu.gym.rest.dto.EquipmentRequestDTO;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import ba.edu.ibu.gym.rest.dto.MembershipRequestDTO;
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
public class MembershipServiceTest {

    @MockBean
    MembershipRepository membershipRepository;

    @Autowired
    MembershipService membershipService;


    @Test
    public void shouldReturnMembershipById(){
        Date date1= new Date();
        Date date2= new Date();

        TrainingPlan trainingPlan= new TrainingPlan(
                "someId2",
                "testTrainingPlan",
                "created for testing purpose",
                "60$"
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
                trainingPlan
        );

        Mockito.when(membershipRepository.findById("someId")).thenReturn(Optional.of(membership));
        MembershipDTO result= membershipService.getMembershipById(membership.getId());
        Assertions.assertThat(membership.getMember().getEmail()).isEqualTo(result.getMemberEmail());
        Assertions.assertThat(membership.getEndDate()).isEqualTo(result.getEndDate());

    }

}
