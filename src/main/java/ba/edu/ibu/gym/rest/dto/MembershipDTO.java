package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;

import java.util.Date;

public class MembershipDTO {
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberImage;
    private Date startDate;
    private Date endDate;
    private String trainingPlanId;
    private String trainingPlanName;
    private String trainingPlanPrice;
    private StatusType statusType;
    public MembershipDTO(Membership membership) {
        this.memberId = membership.getMember().getId();
        this.memberName = membership.getMember().getFirstName() + " " + membership.getMember().getLastName();
        this.memberEmail = membership.getMember().getEmail();
        this.memberImage = membership.getMember().getImage();
        this.startDate = membership.getStartDate();
        this.endDate = membership.getEndDate();
        this.trainingPlanId = membership.getTrainingPlan().getId();
        this.trainingPlanName = membership.getTrainingPlan().getName();
        this.trainingPlanPrice = membership.getTrainingPlan().getPrice();
        this.statusType=membership.getStatusType();
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getTrainingPlanName() {
        return trainingPlanName;
    }

    public void setTrainingPlanName(String trainingPlanName) {
        this.trainingPlanName = trainingPlanName;
    }

    public String getTrainingPlanPrice() {
        return trainingPlanPrice;
    }

    public void setTrainingPlanPrice(String trainingPlanPrice) {
        this.trainingPlanPrice = trainingPlanPrice;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
