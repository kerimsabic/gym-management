package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Member extends User{

    private Trainer trainer;
    private String qrCode;
    private TrainingPlan trainingPlan;
    private Membership membership;

    public Member( Trainer trainer, String qrCode, TrainingPlan trainingPlan, Membership membership) {
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


    public Trainer getTrainer() {
        return trainer;
    }


    public void setTrainer(Trainer trainer) {
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
