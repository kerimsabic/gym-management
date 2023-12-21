package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Membership;

import java.util.Date;

public class MembershipRequestDTO {

    private String memberId;
    private String trainingPlanId;
    private int numOfMonths;

    public MembershipRequestDTO() {

    }

    public MembershipRequestDTO(Membership membership, String memberId, String trainingPlanId, int numOfMonths) {
        this.trainingPlanId = trainingPlanId;
        this.memberId = memberId;
        this.numOfMonths = numOfMonths;

    }
    public MembershipRequestDTO(Membership membership) {
        this.trainingPlanId = trainingPlanId;
        this.memberId = memberId;
        this.numOfMonths = numOfMonths;

    }

    public Membership toEntity() {
        Membership membership = new Membership();
        membership.setStartDate(new Date());
        this.setMemberId(memberId);
        this.setTrainingPlanId(trainingPlanId);
        this.setNumOfMonths(numOfMonths);
        return membership;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }


}
