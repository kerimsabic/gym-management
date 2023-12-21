package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;

import java.util.Date;

public class AttendanceDTO {
    private String id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private String email;
    private String image;
    private Date date;
    private String trainerName;
    private String trainerId;




    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public AttendanceDTO(Attendance member) {
        this.id = member.getId();
        this.firstName = member.getMember().getFirstName();
        this.lastName=member.getMember().getLastName();
        this.userType = member.getMember().getUserType();
        this.email = member.getMember().getEmail();
        this.image=member.getMember().getImage();
        this.date=member.getAttendanceDate();
        this.trainerName=member.getMember().getTrainer().getFirstName()+" "+member.getMember().getTrainer().getLastName() ;
        this.trainerId=member.getMember().getTrainer().getId();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
