package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;

import java.util.List;

public class TrainerDTO {
    private List<MemberDTO> members;
    private String firstName;
    private String lastName;


    public TrainerDTO(Trainer trainer) {


        this.members=trainer.getMembers();
        this.firstName = trainer.getFirstName();
        this.lastName = trainer.getLastName();

    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }
}
