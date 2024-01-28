package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;

public class MemberRequestDTO extends UserRequestDTO {

    private String qrCode;
    private String trainerId;
    private String trainingPlanId;
    private int numOfMonths;

    public MemberRequestDTO(){

    }

    public MemberRequestDTO(Member member, String trainerId, String trainingPlanId, int numOfMonths){
        super(member);
        this.qrCode=member.getQrCode();
        this.trainerId=trainerId;
        this.trainingPlanId=trainingPlanId;
        this.numOfMonths=numOfMonths;
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
        member.setUsername(this.getUsername());
        member.setStatusType(this.getStatusType());
       // member.setTrainer(getTrainerId());
       this.setTrainerId(trainerId);
       this.setTrainingPlanId(trainingPlanId);
       this.setNumOfMonths(numOfMonths);
     //  member.setTrainer();

        return member;

    }


    public String getTrainerId() {
        return trainerId;
    }

    public void
    setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }
}
