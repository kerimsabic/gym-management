package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;

public class PlanRequestDTO {
    private String name;

    private String description;

    private String price;

    private StatusType statusType;
    private String accessTime;
    private String numOfPeople;
    private boolean  water;
    private boolean freeparking;



    public PlanRequestDTO(){

    }

    public PlanRequestDTO(TrainingPlan trainingPlan) {
        this.name=trainingPlan.getName();
        this.description=trainingPlan.getDescription();
        this.price=trainingPlan.getPrice();
        this.statusType=trainingPlan.getStatusType();
        this.freeparking=trainingPlan.isFreeparking();
        this.water=trainingPlan.isWater();
        this.numOfPeople=trainingPlan.getNumOfPeople();
        this.accessTime=trainingPlan.getAccessTime();
    }

    public TrainingPlan toEntity(){
        TrainingPlan trainingPlan=new TrainingPlan();
        trainingPlan.setName(name);
        trainingPlan.setDescription(description);
        trainingPlan.setPrice(price);
        trainingPlan.setStatusType(statusType);
        trainingPlan.setWater(water);
        trainingPlan.setFreeparking(freeparking);
        trainingPlan.setAccessTime(accessTime);
        trainingPlan.setNumOfPeople(numOfPeople);
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

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public String getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(String numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isFreeparking() {
        return freeparking;
    }

    public void setFreeparking(boolean freeparking) {
        this.freeparking = freeparking;
    }
}
