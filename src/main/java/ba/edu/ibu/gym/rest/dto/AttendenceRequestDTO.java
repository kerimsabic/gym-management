package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.User;

import java.util.Date;

public class AttendenceRequestDTO {
   // private Date attendanceDate;
    private String memberId;

    public AttendenceRequestDTO() {

    }

    public AttendenceRequestDTO(Attendance attendance) {
      // this.attendanceDate=attendance.getAttendanceDate();
        this.memberId=attendance.getMemberId();
    }

    public Attendance toEntity(){
        Attendance attendance=new Attendance();
        attendance.setAttendanceDate(new Date());
        attendance.setMemberId(memberId);
        return attendance;
    }



    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
