package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;

import java.util.List;

public class TrainerRequestDTO extends UserRequestDTO{
    private List<MemberDTO> members;



    public  TrainerRequestDTO() { }

    public TrainerRequestDTO(Trainer trainer) {
       // super(trainer);
        this.members=trainer.getMembers();
    }
    public Trainer toEntity(){
        Trainer trainer= new Trainer();

        trainer.setFirstName(this.getFirstName());
        trainer.setLastName(this.getLastName());
        trainer.setEmail(this.getEmail());
        trainer.setAddress(this.getAddress());
        trainer.setImage(this.getImage());
        trainer.setPassword(this.getPassword());
        trainer.setPhone(this.getPhone());
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
