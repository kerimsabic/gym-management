package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;
import ba.edu.ibu.gym.core.repository.MemberRepository;

import ba.edu.ibu.gym.rest.dto.AttendanceDTO;
import ba.edu.ibu.gym.rest.dto.AttendanceRequestDTO;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AttendaceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MembershipService membershipService;

    @InjectMocks
    private AttendanceService attendanceService;

    private Attendance attendance;
    private Member member;
    private MembershipDTO membershipDTO;
    private AttendanceRequestDTO attendanceRequestDTO;
    private Date currentDate;
    private Date pastDate;
    private Date futureDate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        currentDate = new Date();
        pastDate = new Date(currentDate.getTime() - 1000000000L);
        futureDate = new Date(currentDate.getTime() + 1000000000L);

        member = new Member();
        member.setId("1");
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setStatusType(StatusType.OFFLINE);
        member.setEmail("john.doe@example.com");

        attendance = new Attendance();
        attendance.setId("1");
        attendance.setAttendanceDate(currentDate);
        attendance.setMember(member); // Ensure member is set

        membershipDTO = new MembershipDTO();
        membershipDTO.setEndDate(futureDate);

        attendanceRequestDTO = new AttendanceRequestDTO();
        attendanceRequestDTO.setMemberId("1");

    }

    @Test
    public void testGetAllAttendance() {
        // Prepare mock data
        when(attendanceRepository.findAll()).thenReturn(List.of(attendance));

        List<AttendanceDTO> result = attendanceService.getAllAttendance();

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("John", result.get(0).getFirstName()); // Check other fields if necessary
    }

    @Test
    public void testGetAttendanceByMemberId() {
        // Prepare mock data
        when(attendanceRepository.findTop30ByMember_IdOrderByAttendanceDateDesc("1")).thenReturn(List.of(attendance));

        List<AttendanceDTO> result = attendanceService.getAttendanceByMemberId("1");

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetAttendanceById() {
        // Prepare mock data
        when(attendanceRepository.findById("1")).thenReturn(Optional.of(attendance));

        AttendanceDTO result = attendanceService.getAttendanceById("1");

        assertEquals("1", result.getId());
    }

    @Test
    public void testGetAttendanceByIdNotFound() {
        // Prepare mock data
        when(attendanceRepository.findById("1")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            attendanceService.getAttendanceById("1");
        });

        assertEquals("Attendance  with the given ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testGetAttendanceByDate() {
        // Prepare mock data
        when(attendanceRepository.findByAttendanceDateBetween(pastDate, futureDate)).thenReturn(List.of(attendance));

        List<AttendanceDTO> result = attendanceService.getAttendanceByDate(pastDate, futureDate);

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }


    @Test
    public void testRecordAttendanceMembershipExpired() {
        // Prepare mock data
        membershipDTO.setEndDate(pastDate);

        when(memberService.getMemberById2("1")).thenReturn(member);
        when(membershipService.getMembershipByMemberId("1")).thenReturn(membershipDTO);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            attendanceService.recordAttendance(attendanceRequestDTO);
        });

        assertEquals("Membership expired", thrown.getMessage());
    }


}