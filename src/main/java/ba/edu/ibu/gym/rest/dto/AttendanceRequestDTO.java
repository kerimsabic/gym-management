package ba.edu.ibu.gym.rest.dto;



import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.repository.AttendanceRepository;

import java.util.Date;

public class AttendanceRequestDTO {

    private String memberId;

    public AttendanceRequestDTO(){

    }

    public AttendanceRequestDTO(String memberId){
        this.memberId=memberId;

    }

    public AttendanceRequestDTO(Attendance attendance,String memberId){

        this.memberId=memberId;

    }


    public Attendance toEntity(){
        Attendance attendance=new Attendance();
        this.setMemberId(memberId);
        attendance.setAttendanceDate(new Date());
        return attendance;
    }

   /* public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }*/

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
