package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class UserService {
    private  UserRepository userRepository;

   /* The first method of implemetation, need to comment out conditional property in both senders
    @Autowired
    private MailSender mailgunSender;
    @Autowired
    private MailSender sendgridSender;  */

   /* @Autowired
    private MailSender mailSender;*/




    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    public List<UserDTO> getUserAdmins(){
        List<User> adminUsers = userRepository.findByUserType(UserType.ADMIN);
        return  adminUsers.stream().map(UserDTO::new).collect(toList());
    }

    public UserDTO getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new UserDTO(user.get());
    }

    public UserDTO addUser(UserRequestDTO payload) {
        User user = userRepository.save(payload.toEntity());
        user.setUserType(UserType.ADMIN);
        user.setStatusType(StatusType.ONLINE);
        return new UserDTO(user);
    }

    public  UserDTO updateUser(String id, UserRequestDTO payload){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        User updatedUser= payload.toEntity();
        updatedUser.setId(user.get().getId());
        updatedUser=userRepository.save(updatedUser);
        return new UserDTO(updatedUser);
    }

    public void deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }



    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsernameOrEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
            }
        };
    }




}
