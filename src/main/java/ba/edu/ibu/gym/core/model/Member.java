package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Member extends User{

    private User trainer;
    private String qrCode;
    private TrainingPlan trainingPlan;
    private Membership membership;

    public Member(User user, User trainer, String qrCode, TrainingPlan trainingPlan, Membership membership) {
        super(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getUsername(), user.getPhone(), user.getAddress(), user.getImage(), user.getStatusType(), UserType.MEMBER);
        this.trainer = trainer;
        this.qrCode = qrCode;
        this.trainingPlan=trainingPlan;
        this.membership=membership;
    }




    public Member(){

    }

    public Member(User user) {
        super();
    }


    public User getTrainer() {
        return trainer;
    }


    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }
}
