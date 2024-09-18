package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.TrainingPlan;

public class PaymentRequest {
    private String userId;
    private String trainingPlanId;
    private String price;
    private String name;
    private int numOfMonths;

    // Getters and setters


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }
}
