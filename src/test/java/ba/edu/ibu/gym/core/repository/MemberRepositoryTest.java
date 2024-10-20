package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberRepositoryTest {

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase() {
        // Create a Member instance
        Member member1 = new Member();
        member1.setId("1");
        member1.setFirstName("John");
        member1.setLastName("Doe");

        Member member2 = new Member();
        member2.setId("2");
        member2.setFirstName("Jane");
        member2.setLastName("Doe");

        List<Member> members = List.of(member1, member2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> page = new PageImpl<>(members, pageable, members.size());

        when(memberRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "Doe", pageable))
                .thenReturn(page);

        Page<Member> result = memberRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "Doe", pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
        assertEquals("Jane", result.getContent().get(1).getFirstName());
    }

    @Test
    public void testFindByStatusType() {
        // Create a Member instance
        Member member = new Member();
        member.setId("1");
        member.setStatusType(StatusType.ONLINE);

        List<Member> members = List.of(member);

        when(memberRepository.findByStatusType(StatusType.ONLINE)).thenReturn(members);

        List<Member> result = memberRepository.findByStatusType(StatusType.ONLINE);
        assertFalse(result.isEmpty());
        assertEquals(StatusType.ONLINE, result.get(0).getStatusType());
    }



    @Test
    public void testFindByEmail() {
        // Create a Member instance
        Member member = new Member();
        member.setId("1");
        member.setEmail("john.doe@example.com");

        when(memberRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(member));

        Optional<Member> result = memberRepository.findByEmail("john.doe@example.com");
        assertTrue(result.isPresent());
        assertEquals("john.doe@example.com", result.get().getEmail());
    }



    @Test
    public void testFindByFirstNameContaining() {
        // Create a Member instance
        Member member = new Member();
        member.setId("1");
        member.setFirstName("John");

        List<Member> members = List.of(member);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> page = new PageImpl<>(members, pageable, members.size());

        when(memberRepository.findByFirstNameContaining("John", pageable)).thenReturn(page);

        Page<Member> result = memberRepository.findByFirstNameContaining("John", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }
}
