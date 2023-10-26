package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;

public class MemberRequestDTO extends UserRequestDTO {
    private String trainerId;

    public MemberRequestDTO(){

    }

    public MemberRequestDTO(Member member, String trainerId){

        this.trainerId=trainerId;
    }

    public Member toEntity(){
        Member member=new Member();
        return member;

    }


    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }
}
