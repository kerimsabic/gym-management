package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.*;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Service
public class AttendanceService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private AttendanceRepository attendanceRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private MembershipService membershipService;
    public AttendanceService(AttendanceRepository attendanceRepository, MemberService memberService, MemberRepository memberRepository, MembershipService membershipService){
        this.attendanceRepository=attendanceRepository;
        this.memberService=memberService;
        this.memberRepository=memberRepository;
        this.membershipService=membershipService;
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

    public List<AttendanceDTO> getAttendanceByDate(Date startdate, Date endDate){
        List<Attendance> attendance = attendanceRepository.findByAttendanceDateBetween(startdate, endDate);
        return attendance
                .stream()
                .map(AttendanceDTO::new)
                .collect(toList());
    }

    public AttendanceDTO recordAttendance(AttendanceRequestDTO payload){

        String memberId= payload.getMemberId();

        Member member= memberService.getMemberById2(memberId);
        Date currentDate = new Date();
        MembershipDTO membership=membershipService.getMembershipByMemberId(memberId);
        Date membershipEndDate=membership.getEndDate();
        if(membershipEndDate.before(currentDate)){                                                  //checking if membership is not expired
            throw new ResourceNotFoundException("Membership expired");
        }
        else{
            Attendance attendance= payload.toEntity();
            attendance.setMember(member);
            member.setStatusType(StatusType.ONLINE);
            memberRepository.save(member);

            attendanceRepository.save(attendance);


            scheduler.schedule(() -> {
                // Retrieve the member again to avoid issues with detached entities
                Member updatedMember = memberService.getMemberById2(memberId);
                if (updatedMember.getStatusType() == StatusType.ONLINE) {
                    updatedMember.setStatusType(StatusType.OFFLINE);
                    memberRepository.save(updatedMember);
                }
            }, 4, TimeUnit.MINUTES);                //set this to hours, minutes just for demonstration

            return new AttendanceDTO(attendance);
        }


    }

    public void deleteAttendance(String id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        attendance.ifPresent(attendanceRepository::delete);

    }
}
