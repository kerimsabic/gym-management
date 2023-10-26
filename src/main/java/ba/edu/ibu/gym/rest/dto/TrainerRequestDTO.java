package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;

import java.util.List;

public class TrainerRequestDTO extends UserRequestDTO{
    private List<MemberDTO> members;



    public  TrainerRequestDTO() { }

    public TrainerRequestDTO(Trainer trainer) {
        this.members=trainer.getMembers();
    }
    public Trainer toEntity(){
        Trainer trainer= new Trainer();
        trainer.setMembers(members);
        return trainer;
    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }
}
