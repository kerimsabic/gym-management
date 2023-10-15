package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Member;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
@Repository
public class MemberRepository {
    private List<Member>members;

//some dummy data to check out if it is working
    public MemberRepository() {
        this.members= Arrays.asList(
                new Member(1, "John", "Doe", "john@example.com", "123-456-7890", "123 Gym Street", 1),
                new Member(2, "Jane", "Smith", "jane@example.com", "555-555-5555", "456 Fitness Avenue", 2),
                new Member(3, "Tom", "Johnson", "tom@example.com", "777-777-7777", "789 Workout Lane", 3)

        );
    }

    public List<Member> findAll(){
        return members;
    }
    public Member findById(int id){
        return members.stream().filter(member -> member.getId()==id).findFirst().orElse(null);
    }
}
