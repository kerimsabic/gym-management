package ba.edu.ibu.gym.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Membership {
    @Id
    private String id;
    private Member member;

    private Date startDate;
    private Date endDate;
    private TrainingPlan trainingPlan;


    public Membership(String id, Member member, Date startDate, Date endDate, TrainingPlan trainingPlan) {
        this.id = id;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainingPlan = trainingPlan;
    }

    public Membership(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
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

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }
}
