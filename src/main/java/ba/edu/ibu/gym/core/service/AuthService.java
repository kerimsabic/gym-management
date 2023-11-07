package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.TrainerRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.LoginDTO;
import ba.edu.ibu.gym.rest.dto.LoginRequestDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository,MemberRepository memberRepository, TrainerRepository trainerRepository) {
        this.userRepository = userRepository;
        this.memberRepository=memberRepository;
        this.trainerRepository=trainerRepository;
    }

    public UserDTO signUp(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );
        User user = userRequestDTO.toEntity();


        userRepository.save(user);

        if(user.getUserType().equals(UserType.MEMBER)){
            Member member= new Member();
            member.setId(user.getId());
            member.setFirstName(user.getFirstName());
            member.setLastName(user.getLastName());
            member.setEmail(user.getEmail());
            member.setPassword(user.getPassword());
            member.setPhone(user.getPhone());
            member.setAddress(user.getAddress());
            member.setImage(user.getImage());
            member.setUsername(user.getUsername());
         //   member.setTrainer(null); // mozda dodati ovo
            memberRepository.save(member);
        }
        else if (user.getUserType().equals(UserType.TRAINER)) {
            Trainer trainer=new Trainer();
            trainer.setId(user.getId());
            trainer.setFirstName(user.getFirstName());
            trainer.setLastName(user.getLastName());
            trainer.setEmail(user.getEmail());
            trainer.setPassword(user.getPassword());
            trainer.setPhone(user.getPhone());
            trainer.setAddress(user.getAddress());
            trainer.setImage(user.getImage());
            trainer.setUsername(user.getUsername());
            trainerRepository.save(trainer);
        }

        //moras popravit trainer value u memberima da moze trainer biti null

        return new UserDTO(user);
    }

    public LoginDTO signIn(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist."));
        String jwt = jwtService.generateToken(user);

        return new LoginDTO(jwt);
    }

}
