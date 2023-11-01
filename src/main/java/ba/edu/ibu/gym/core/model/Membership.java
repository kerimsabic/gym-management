package ba.edu.ibu.gym.core.model;


import ba.edu.ibu.gym.rest.dto.MemberDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Membership {
    private String id;
    private MemberDTO member;
    private Date startDate;
    private Date endDate;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
