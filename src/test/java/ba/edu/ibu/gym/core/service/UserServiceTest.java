package ba.edu.ibu.gym.core.service;
import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");
        user.setUsername("user1");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("updatedUser");
    }

    @Test
    public void testGetUsers() {
        // Prepare mock data
        User user2 = new User();
        user2.setId("2");
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        List<UserDTO> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        // Prepare mock data
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById("1");

        assertEquals("1", result.getId());
        assertEquals("user1", result.getUsername());
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Prepare mock data
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById("1");
        });

        assertEquals("The user with the given ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testAddUser() {
        // Prepare mock data
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.addUser(userRequestDTO);

        assertEquals("1", result.getId());
        assertEquals("user1", result.getUsername());
    }



    @Test
    public void testDeleteUser() {
        // Prepare mock data
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUser("1");

        // Verify that delete was called
        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Prepare mock data
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        userService.deleteUser("1");

        // Verify that delete was not called
        verify(userRepository, never()).delete(any(User.class));
    }
}

