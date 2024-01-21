package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Attendance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AttendanceRepositoryTest {

    @Autowired
    private  AttendanceRepository attendanceRepository;



    @Test
    public void returnsAllMarkedAttendance(){
        List<Attendance> attendanceList = attendanceRepository.findAll();
        Assertions.assertEquals(39,attendanceList.size());
    }
}
