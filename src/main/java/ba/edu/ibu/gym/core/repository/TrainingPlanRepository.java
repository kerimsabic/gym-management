package ba.edu.ibu.gym.core.repository;


import ba.edu.ibu.gym.core.model.TrainingPlan;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TrainingPlanRepository {
    private List<TrainingPlan> plans;

    //some dummy data to check out if it is working
    public TrainingPlanRepository() {
        this.plans= Arrays.asList(
                new TrainingPlan(1, "Classic", "A training plan for beginners", "60$"),
                new TrainingPlan(2, "Half-day", "A training plan for beginners", "30$"),
                new TrainingPlan(3, "Students", "A training plan for beginners", "50$"),
                new TrainingPlan(4, "Custom Plan", "A training plan for beginners", "75$")
        );
    }

    public List<TrainingPlan> findAll(){
        return plans;
    }
    public TrainingPlan findById(int id){
        return plans.stream().filter(member -> member.getId()==id).findFirst().orElse(null);
    }
}
