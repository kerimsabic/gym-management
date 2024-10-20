package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.*;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.*;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainingPlanService trainingPlanService;
    private final UserService userService;
    private final MemberService memberService;
    private final MembershipRepository membershipRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository,MemberRepository memberRepository, TrainingPlanService trainingPlanService,
                       UserService userService, MemberService membershipService, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.memberRepository=memberRepository;
        this.trainingPlanService=trainingPlanService;
        this.userService=userService;
        this.memberService=membershipService;
        this.membershipRepository=membershipRepository;
    }

    public UserDTO signUpTrainer(UserRequestDTO trainerRequestDTO) {
        trainerRequestDTO.setPassword(passwordEncoder.encode(trainerRequestDTO.getPassword()));
        User trainer = trainerRequestDTO.toEntity();
        trainer.setUserType(UserType.TRAINER);
        userRepository.save(trainer);
        return new UserDTO(trainer);
    }

    public UserDTO signUpAdmin(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User user = userRequestDTO.toEntity();
        user.setUserType(UserType.ADMIN);
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO updateAdmin(String id, UserRequestDTO payload){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The admin with the given ID does not exist.");
        }
        User updatedUser= payload.toEntity();
        updatedUser.setId(user.get().getId());

        if(payload.getPassword()==null ){
            updatedUser.setPassword(user.get().getPassword());
        }
        else{
            updatedUser.setPassword(
                    passwordEncoder.encode(payload.getPassword())
            );
        }
        updatedUser=userRepository.save(updatedUser);
        return new UserDTO(updatedUser);
    }

    public MemberDTO signUpMember(MemberRequestDTO memberRequestDTO) {
        memberRequestDTO.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        Member member = memberRequestDTO.toEntity();

        String trainingPlanId = memberRequestDTO.getTrainingPlanId();
        TrainingPlan trainingPlan = trainingPlanService.getPlanById(trainingPlanId);
        member.setTrainingPlan(trainingPlan);

        if (memberRequestDTO.getTrainerId() != null) {
            String trainerId = memberRequestDTO.getTrainerId();
            User trainer = userService.getUserById2(trainerId);
            member.setTrainer(trainer);
        }

        member.setUserType(UserType.MEMBER);

        userRepository.save(member);
        memberRepository.save(member);

        if(member!=null && member.getId()!=null){
            int num=memberRequestDTO.getNumOfMonths();
            String email=member.getEmail();
            Membership membership=memberService.createMembershipOnMemberCreation(email,num,trainingPlanId);
            membershipRepository.save(membership);
            System.out.println(membership.getEndDate());
        }

        userRepository.save(member);
        memberRepository.save(member);
        return new MemberDTO(member);
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
