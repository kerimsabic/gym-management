package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataMongoTest
public class AttendanceRepositoryTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private AttendanceRepositoryTest attendanceRepositoryTest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAttendanceDateBetween() {
        Date startDate = new Date();
        Date endDate = new Date();

        // Prepare mock data
        Member member = new Member(); // Initialize member if needed
        Attendance attendance1 = new Attendance("1", startDate, member);
        Attendance attendance2 = new Attendance("2", endDate, member);
        List<Attendance> mockAttendances = List.of(attendance1, attendance2);


        when(attendanceRepository.findByAttendanceDateBetween(startDate, endDate)).thenReturn(mockAttendances);

        List<Attendance> result = attendanceRepository.findByAttendanceDateBetween(startDate, endDate);

        verify(attendanceRepository).findByAttendanceDateBetween(startDate, endDate);
        assertEquals(mockAttendances, result);
    }

    @Test
    public void testFindTop30ByMember_IdOrderByAttendanceDateDesc() {
        String memberId = "someMemberId";

        // Prepare mock data
        Member member = new Member(); // Initialize member if needed
        Attendance attendance1 = new Attendance("1", new Date(System.currentTimeMillis() - 10000), member); // Recent date
        Attendance attendance2 = new Attendance("2", new Date(System.currentTimeMillis() - 50000), member); // Older date
        List<Attendance> mockAttendances = List.of(attendance1, attendance2);


        when(attendanceRepository.findTop30ByMember_IdOrderByAttendanceDateDesc(memberId)).thenReturn(mockAttendances);

        // Call the method under test
        List<Attendance> result = attendanceRepository.findTop30ByMember_IdOrderByAttendanceDateDesc(memberId);

        // Verify the results
        verify(attendanceRepository).findTop30ByMember_IdOrderByAttendanceDateDesc(memberId);
        assertEquals(mockAttendances, result);
    }
}
