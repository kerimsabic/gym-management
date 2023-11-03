package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;

public class MemberDTO extends UserDTO{
    private String qrCode;

    private String trainerEmail;
    private String TrainerImage;
    private String trainerName;
    private String trainerId;
    private UserType trainerUserType;




    public MemberDTO(Member member) {
        super(member);
        //this.trainer=member.getTrainer();
        this.trainerName=member.getTrainer().getFirstName()+" "+member.getTrainer().getLastName();
        this.trainerEmail=member.getTrainer().getEmail();
        this.trainerId=member.getTrainer().getId();
        this.trainerUserType=member.getTrainer().getUserType();

       // this.trainer=new TrainerDTO(member.getTrainer()) //does not work when I put trainerDTO
    }


   /* public Trainer getTrainer() {
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
    }*/


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

    public String getTrainerImage() {
        return TrainerImage;
    }

    public void setTrainerImage(String trainerImage) {
        TrainerImage = trainerImage;
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

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public UserType getTrainerUserType() {
        return trainerUserType;
    }

    public void setTrainerUserType(UserType trainerUserType) {
        this.trainerUserType = trainerUserType;
    }

}
