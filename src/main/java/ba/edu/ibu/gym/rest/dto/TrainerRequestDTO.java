package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;

import java.util.List;

public class TrainerRequestDTO extends UserRequestDTO{
    private List<Member> members;

    public  TrainerRequestDTO() { }

    public TrainerRequestDTO(Trainer trainer) {
        super(trainer);
        this.members=trainer.getMembers();
    }
    public Trainer toEntity(){
        Trainer trainer= new Trainer();

        trainer.setFirstName(this.getFirstName());
        trainer.setUsername(this.getUsername());
        trainer.setLastName(this.getLastName());
        trainer.setEmail(this.getEmail());
        trainer.setAddress(this.getAddress());
        trainer.setImage(this.getImage());
        trainer.setPassword(this.getPassword());
        trainer.setPhone(this.getPhone());
        trainer.setUserType(UserType.TRAINER);
        trainer.setMembers(members);
        return trainer;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
