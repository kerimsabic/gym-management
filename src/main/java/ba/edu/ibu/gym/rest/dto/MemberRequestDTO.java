package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;

public class MemberRequestDTO extends UserRequestDTO {

    private String qrCode;
    private String trainerId;

    public MemberRequestDTO(){

    }

    public MemberRequestDTO(Member member, String trainerId){
        super(member);
        this.qrCode=member.getQrCode();
        this.trainerId=trainerId;
    }

    public Member toEntity(){
        Member member=new Member();
        member.setFirstName(this.getFirstName());
        member.setLastName(this.getLastName());
        member.setEmail(this.getEmail());
        member.setAddress(this.getAddress());
        member.setImage(this.getImage());
        member.setPassword(this.getPassword());
        member.setPhone(this.getPhone());
        member.setQrCode(this.qrCode);
       // member.setTrainer(getTrainerId());
       this.setTrainerId(trainerId);
     //  member.setTrainer();

        return member;

    }


    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
