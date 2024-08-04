package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.PasswordDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PasswordResetService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;


    public UserDTO updateUserPassword(String memberId, PasswordDTO passwordDTO) {
        Optional<User> memberOpt = userRepository.findById(memberId);
        /* Optional<User>userOpt= userRepository.findById(memberId);*/

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
    }
}
