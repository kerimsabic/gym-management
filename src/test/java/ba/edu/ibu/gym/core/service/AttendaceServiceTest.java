package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import ba.edu.ibu.gym.rest.dto.AttendanceDTO;
import ba.edu.ibu.gym.rest.dto.AttendanceRequestDTO;
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
public class AttendaceServiceTest {

    @MockBean
    AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceService attendanceService;

   /* @Test
    public void shouldReturnAttendanceWhenMarked(){
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

        Mockito.when(attendanceRepository.save(ArgumentMatchers.any(Attendance.class))).thenReturn(attendance);

        AttendanceDTO recordedAttendance= attendanceService.recordAttendance(new AttendanceRequestDTO("someId"));
        Assertions.assertThat(attendance.getId()).isEqualTo(recordedAttendance.getId());
        Assertions.assertThat(attendance.getMember().getFirstName()).isEqualTo(recordedAttendance.getFirstName());
        Assertions.assertThat(attendance.getMember().getEmail()).isEqualTo(recordedAttendance.getEmail());
    }*/

    @Test
    public void shouldReturnAttendanceById(){

        Member member1 = new Member();
        member1.setFirstName("Kerim");
        member1.setLastName("Sabic");
        member1.setId("someId");
        member1.setAddress("Sarajevo");
        member1.setPhone("11111");
        member1.setUserType(UserType.MEMBER);
        member1.setEmail("kerimsabic.com");
        member1.setTrainer(null);

        Date date1= new Date();

        Attendance attendance= new Attendance(
                "someId",
                date1,
                member1

        );

        Mockito.when(attendanceRepository.findById("someId")).thenReturn(Optional.of(attendance));
        AttendanceDTO result= attendanceService.getAttendanceById(attendance.getId());
        if (attendance.getMember().getTrainer() != null) {
            Assertions.assertThat(attendance.getMember().getTrainer().getId())
                    .isEqualTo(result.getTrainerId());
        }
        Assertions.assertThat(attendance.getMember().getEmail()).isEqualTo(result.getEmail());
        /*Assertions.assertThat(trainingPlan.getDescription()).isEqualTo(result.getDescription());
        Assertions.assertThat(trainingPlan.getPrice()).isEqualTo(result.getPrice());*/
    }
}
