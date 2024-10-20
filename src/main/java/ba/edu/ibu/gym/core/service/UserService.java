package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.MemberDTO;
import ba.edu.ibu.gym.rest.dto.PasswordDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class UserService {
    private  UserRepository userRepository;
    private JwtService jwtService;

   /* The first method of implemetation, need to comment out conditional property in both senders
    @Autowired
    private MailSender mailgunSender;
    @Autowired
    private MailSender sendgridSender;  */

   /* @Autowired
    private MailSender mailSender;*/



    @Autowired
    private EmailService emailService;




    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService=jwtService;
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

    public List<UserDTO> getUserMembers(){
        List<User> adminUsers = userRepository.findByUserType(UserType.MEMBER);
        return  adminUsers.stream().map(UserDTO::new).collect(toList());
    }

    public List<UserDTO> getUserTrainers(){
        List<User> adminUsers = userRepository.findByUserType(UserType.TRAINER);
        return  adminUsers.stream().map(UserDTO::new).collect(toList());
    }

    public UserDTO getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new UserDTO(user.get());
    }

    public User getUserById2(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return user.get();
    }

    public UserDTO getUserByToken(String token){

        String userEmail=jwtService.extractUserName(token);
        System.out.println(userEmail);
        Optional<User> user = userRepository.findByUsernameOrEmail(userEmail);
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

   /* public UserDTO updateUserPassword(String memberId, PasswordDTO passwordDTO) {
        Optional<User> memberOpt = userRepository.findById(memberId);


        if (memberOpt.isPresent()) {
            User member = memberOpt.get();

            String newPassword = passwordDTO.getPassword();
            String repeatedPassword = passwordDTO.getRepeatedPassword();

            if (newPassword == null || !newPassword.equals(repeatedPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            String encodedPassword = passwordEncoder.encode(newPassword);
            member.setPassword(encodedPassword);


            userRepository.save(member);

            emailService.sendEmail("kerim.sabic@stu.ibu.edu.ba", member.getFirstName(), "Password change confirmation", "Your password has been successfully changed!", "Password has been changed");

            return new UserDTO(member);
        } else {
            throw new ResourceNotFoundException("user not found");
        }
    }*/



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
