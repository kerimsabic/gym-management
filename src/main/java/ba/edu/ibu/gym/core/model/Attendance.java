package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.rest.dto.MemberDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Attendance {

    @Id
    private String id;

    private Date attendanceDate;
    private Member member;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
