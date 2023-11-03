package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.*;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;
    private MemberService memberService;

    public AttendanceService(AttendanceRepository attendanceRepository, MemberService memberService){
        this.attendanceRepository=attendanceRepository;
        this.memberService=memberService;
    }

    public List<AttendanceDTO> getAllAttendance(){
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances
                .stream()
                .map(AttendanceDTO::new)
                .collect(toList());
    }

    public AttendanceDTO getAttendanceById(String id){
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if(attendance.isEmpty()){
            throw new ResourceNotFoundException("Attendance  with the given ID does not exist.");
        }
        return new AttendanceDTO(attendance.get());
    }

    public AttendanceDTO recordAttendance(AttendanceRequestDTO payload){

        String memberId= payload.getMemberId();

        Member member= memberService.getMemberById2(memberId);

        Attendance attendance= payload.toEntity();
        attendance.setMember(member);

        attendanceRepository.save(attendance);
        return new AttendanceDTO(attendance);
    }

    public void deleteAttendance(String id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        attendance.ifPresent(attendanceRepository::delete);

    }
}
