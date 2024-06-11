package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.StatusType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TrainingPlan {
    @Id
    private String id;
    private String name;
    private String description;
    private String price;

    private String accessTime;
    private String numOfPeople;
    private boolean  water;
    private boolean freeparking;
    private StatusType statusType;

   

    public TrainingPlan(){}

    public TrainingPlan(String id, String name, String description, String price, StatusType statusType, String accessTime, String numOfPeople, boolean water, boolean freeparking) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statusType=statusType;
        this.accessTime=accessTime;
        this.numOfPeople=numOfPeople;
        this.water=water;
        this.freeparking=freeparking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
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

    public void setFreeparking(boolean freeparking) {
        this.freeparking = freeparking;
    }
    

    public boolean isFreeparking() {
        return freeparking;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }
}
