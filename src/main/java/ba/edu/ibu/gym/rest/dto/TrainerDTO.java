package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Trainer;


import java.util.List;

public class TrainerDTO extends UserDTO{
    private List<MemberDTO> members;


    public TrainerDTO(Trainer trainer) {
        super(trainer);
        this.members=trainer.getMembers();


    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }

   /* @Override
    public void setUserType(UserType userType) {
        super.setUserType(UserType.TRAINER);
    }*/
}
