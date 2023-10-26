package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Member extends User{
    private final UserType userType=UserType.MEMBER;

    private Trainer trainer;
    private String qrCode;





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
}
