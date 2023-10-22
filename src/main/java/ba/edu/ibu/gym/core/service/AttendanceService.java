package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.AttendenceRequestDTO;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;    //need this so I can add existing user instead of regular String

    public AttendanceService(AttendanceRepository attendanceRepository,UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository=userRepository;
    }

    public List<Attendance> getAll(){
        return attendanceRepository.findAll();
    }

    public Attendance getAttendenceById(String id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isEmpty()) {
            throw new ResourceNotFoundException("Attendance with the given ID does not exist.");

        }
        return attendance.get();
    }

    public Attendance createAttendance(AttendenceRequestDTO attendenceRequestDTO) {
        String userId = attendenceRequestDTO.getMemberId();
        Date date = new Date();

        Optional<User> user=userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("Attendance with the given ID does not exist.");
        }
        Attendance attendance = new Attendance();
        attendance.setMemberId(user.get().getId());
        attendance.setAttendanceDate(date);

         attendance = attendanceRepository.save(attendenceRequestDTO.toEntity());
        return attendance;
    }


  /*   No need to update attendece
  public Attendance updateAttendece(String id, AttendenceRequestDTO attendenceRequestDTO) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isEmpty()) {
            //throw new exception;
        }
        Attendance updatatedAttendence = attendenceRequestDTO.toEntity();
        updatatedAttendence.setId(attendance.get().getId());
        updatatedAttendence = attendanceRepository.save(updatatedAttendence);
        return updatatedAttendence;
    }*/

    public void deleteAttendence(String id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        attendance.ifPresent(attendanceRepository::delete);

    }
}
