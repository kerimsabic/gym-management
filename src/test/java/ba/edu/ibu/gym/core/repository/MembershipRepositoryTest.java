package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataMongoTest
public class MembershipRepositoryTest {

    @Mock
    private MembershipRepository membershipRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindMembershipByMemberIdFound() {
        // Create a Membership instance
        User user = new User(); // Mock or create a proper User instance as needed
        TrainingPlan trainingPlan = new TrainingPlan(); // Mock or create a proper TrainingPlan instance as needed
        Membership membership = new Membership(
                "1",
                user,
                new Date(),
                new Date(System.currentTimeMillis() + 100000000L), // Future date
                trainingPlan,
                StatusType.ONLINE
        );

        // Mock the behavior of findMembershipByMember_Id
        when(membershipRepository.findMembershipByMember_Id("member123")).thenReturn(Optional.of(membership));

        // Simulate the repository call and verify the result
        Optional<Membership> foundMembership = membershipRepository.findMembershipByMember_Id("member123");
        assertTrue(foundMembership.isPresent());
        Membership result = foundMembership.get();
        assertEquals("1", result.getId());
        assertEquals(user, result.getMember());
        assertEquals(trainingPlan, result.getTrainingPlan());
        assertEquals(StatusType.ONLINE, result.getStatusType());
    }

    @Test
    public void testFindMembershipByMemberIdNotFound() {

        when(membershipRepository.findMembershipByMember_Id("invalidMemberId")).thenReturn(Optional.empty());


        Optional<Membership> foundMembership = membershipRepository.findMembershipByMember_Id("invalidMemberId");
        assertFalse(foundMembership.isPresent());
    }

    @Test
    public void testFindMembershipByMemberId() {
        // Create a Membership instance
        User user = new User(); // Mock or create a proper User instance as needed
        TrainingPlan trainingPlan = new TrainingPlan(); // Mock or create a proper TrainingPlan instance as needed
        Membership membership = new Membership(
                "1",
                user,
                new Date(),
                new Date(System.currentTimeMillis() + 100000000L), // Future date
                trainingPlan,
                StatusType.ONLINE
        );

        // Mock the behavior of findMembershipByMember_Id
        when(membershipRepository.findMembershipByMember_Id("member123")).thenReturn(Optional.of(membership));

        // Call the repository method
        membershipRepository.findMembershipByMember_Id("member123");

        // Verify that the method was called exactly once with the correct parameter
        verify(membershipRepository, times(1)).findMembershipByMember_Id("member123");
    }
}
