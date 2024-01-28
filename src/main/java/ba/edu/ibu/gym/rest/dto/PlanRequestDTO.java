package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;

public class PlanRequestDTO {
    private String name;

    private String description;

    private String price;

    private StatusType statusType;


    public PlanRequestDTO(){

    }

    public PlanRequestDTO(TrainingPlan trainingPlan) {
        this.name=trainingPlan.getName();
        this.description=trainingPlan.getDescription();
        this.price=trainingPlan.getPrice();
        this.statusType=trainingPlan.getStatusType();
    }

    public TrainingPlan toEntity(){
        TrainingPlan trainingPlan=new TrainingPlan();
        trainingPlan.setName(name);
        trainingPlan.setDescription(description);
        trainingPlan.setPrice(price);
        trainingPlan.setStatusType(statusType);
        return trainingPlan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public StatusType getStatusType() {
        return statusType;
    }
}
