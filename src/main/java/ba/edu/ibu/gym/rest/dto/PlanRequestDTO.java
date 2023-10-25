package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.TrainingPlan;

public class PlanRequestDTO {
    private String name;

    private String description;

    private String price;


    public PlanRequestDTO(){

    }

    public PlanRequestDTO(TrainingPlan trainingPlan) {
        this.name=trainingPlan.getName();
        this.description=trainingPlan.getDescription();
        this.price=trainingPlan.getPrice();
    }

    public TrainingPlan toEntity(){
        TrainingPlan trainingPlan=new TrainingPlan();
        trainingPlan.setName(name);
        trainingPlan.setDescription(description);
        trainingPlan.setPrice(price);
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
}
