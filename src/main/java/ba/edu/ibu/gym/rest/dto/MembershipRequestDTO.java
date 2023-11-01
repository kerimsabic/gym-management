package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Membership;

import javax.xml.crypto.Data;
import java.util.Date;

public class MembershipRequestDTO {
    private String memberId;
    private Date startDate;
    private int months;



    public MembershipRequestDTO(Membership membership, String memberId, int months) {
        this.memberId = memberId;
        this.months=months;
        this.startDate=membership.getStartDate();
    }

    public Membership toEntity(){
        Membership membership= new Membership();
        membership.setStartDate(new Date());

       this.setEndDate(months);
        this.setMemberId(memberId);

        return  membership;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public void setEndDate(int months) {
        this.months = months;
    }
}
