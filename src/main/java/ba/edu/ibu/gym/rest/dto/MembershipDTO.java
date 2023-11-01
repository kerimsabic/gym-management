package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Membership;

import java.util.Date;

public class MembershipDTO {
    private MemberDTO member;
    private Date startDate;
    private int months;

    public MembershipDTO(Membership membership) {
    }
    public MembershipDTO(){

    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return months;
    }

    public void setEndDate(Date endDate) {
        this.months = months;
    }
}
