package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.Membership;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MembershipRepositoryTest {

    @Autowired
    private  MembershipRepository membershipRepository;


    @Test
    public void returnsAllMemberships(){
        List<Membership> memberships = membershipRepository.findAll();
        Assertions.assertEquals(1,memberships.size());
    }
}
