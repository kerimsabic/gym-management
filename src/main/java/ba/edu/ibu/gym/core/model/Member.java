package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Member extends User{
   // private final UserType userType=UserType.MEMBER;
    private Trainer trainer;
    private String qrCode;





    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }


   /* public  void serTrainer2(TrainerDTO trainerDTO){
        this.trainer = trainer;

    }*/


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
