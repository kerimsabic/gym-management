package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;

public class MemberDTO extends UserDTO{
    private String qrCode;

    private Trainer trainer;

    public MemberDTO(Member member) {
        super(member);
        this.trainer=member.getTrainer();
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
}
