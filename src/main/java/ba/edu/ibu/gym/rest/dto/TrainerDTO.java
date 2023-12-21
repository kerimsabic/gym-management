package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;


import java.util.List;

public class TrainerDTO extends UserDTO{
    private List<Member> members;



    public TrainerDTO(Trainer trainer) {
        super(trainer);
        this.members=trainer.getMembers();


    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

   /* @Override
    public void setUserType(UserType userType) {
        super.setUserType(UserType.TRAINER);
    }*/
}
