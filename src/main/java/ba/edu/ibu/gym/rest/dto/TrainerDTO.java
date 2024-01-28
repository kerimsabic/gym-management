package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainerDTO extends UserDTO{

    //private HashMap<String,String> memberInformation; tried using hash map to store the information but doesn't make sense
    private List<String> memberInformation;



    public TrainerDTO(Trainer trainer) {
        super(trainer);

        if(trainer.getMembers()!=null){
            this.memberInformation = new ArrayList<>();

            for (Member member : trainer.getMembers()) {
                this.memberInformation.add(member.getFirstName()+" "+member.getLastName()+ " "+ member.getEmail());


            }
        }



        }

    public List<String> getMemberInformation() {
        return memberInformation;
    }

    public void setMemberInformation(List<String> memberInformation) {
        this.memberInformation = memberInformation;
    }

   /* public List<String> getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(List<String> memberEmail) {
        this.memberEmail = memberEmail;
    }*/
    /* @Override
    public void setUserType(UserType userType) {
        super.setUserType(UserType.TRAINER);
    }*/
}
