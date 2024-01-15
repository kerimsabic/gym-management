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

import java.util.ArrayList;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingPlanService trainingPlanService;
    private final TrainerService trainerService;
    private final MembershipService membershipService;
    private final MembershipRepository membershipRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository,MemberRepository memberRepository, TrainerRepository trainerRepository, TrainingPlanService trainingPlanService,
                       TrainerService trainerService, MembershipService membershipService, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.memberRepository=memberRepository;
        this.trainerRepository=trainerRepository;
        this.trainingPlanService=trainingPlanService;
        this.trainerService=trainerService;
        this.membershipService=membershipService;
        this.membershipRepository=membershipRepository;
    }

    public UserDTO signUp(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );
        User user = userRequestDTO.toEntity();
        userRepository.save(user);

        if (user.getUserType().equals(UserType.TRAINER)) {
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
            trainer.setMembers(new ArrayList<Member>());
            trainerRepository.save(trainer);
        }

        //moras popravit trainer value u memberima da moze trainer biti null

        return new UserDTO(user);
    }

    public UserDTO signUpAdmin(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );
        User user = userRequestDTO.toEntity();
        userRepository.save(user);
        user.setUserType(UserType.ADMIN);
        return new UserDTO(user);
    }

    public MemberDTO signUpMember(MemberRequestDTO memberRequestDTO) {
        memberRequestDTO.setPassword(
                passwordEncoder.encode(memberRequestDTO.getPassword())
        );
        Member member = memberRequestDTO.toEntity();
        String trainingPlanId=memberRequestDTO.getTrainingPlanId();
        TrainingPlan trainingPlan = trainingPlanService.getPlanById(trainingPlanId);

        if(memberRequestDTO.getTrainerId()!=null){
            String trainerId= memberRequestDTO.getTrainerId();      //without this for trainer and training plan it would register the member in the database but in the response on swagger both id would be null
            Trainer trainer= trainerService.getTrainerById2(trainerId);
            member.setTrainer(trainer);
        }
        member.setTrainingPlan(trainingPlan);
        member.setUserType(UserType.MEMBER);
        Integer num=memberRequestDTO.getNumOfMonths();

        memberRepository.save(member);
        userRepository.save(member);

        if(member!=null && member.getId()!=null){           //if member successfully saved to the database
            String memberId=member.getId();
            Membership membership=membershipService.createMembershipOnMemberCreation(memberId,num,trainingPlanId);
            membershipRepository.save(membership);
        }


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
