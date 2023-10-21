package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.api.mailsender.MailSender;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class UserService {
    private final UserRepository userRepository;

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

    public UserDTO addUser(UserRequestDTO payload) {
        User user = userRepository.save(payload.toEntity());
        return new UserDTO(user);
    }



}
