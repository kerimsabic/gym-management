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
    private StatusType statusType;



    public TrainingPlan(){}

    public TrainingPlan(String id, String name, String description, String price, StatusType statusType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.statusType=statusType;
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

}
