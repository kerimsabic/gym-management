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
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingPlanService trainingPlanService;
    private final TrainerService trainerService;
    private final MemberService memberService;
    private final MembershipRepository membershipRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository,MemberRepository memberRepository, TrainerRepository trainerRepository, TrainingPlanService trainingPlanService,
                       TrainerService trainerService, MemberService membershipService, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.memberRepository=memberRepository;
        this.trainerRepository=trainerRepository;
        this.trainingPlanService=trainingPlanService;
        this.trainerService=trainerService;
        this.memberService=membershipService;
        this.membershipRepository=membershipRepository;
    }

    public TrainerDTO signUp(TrainerRequestDTO trainerRequestDTO) {
        trainerRequestDTO.setPassword(
                passwordEncoder.encode(trainerRequestDTO.getPassword())
        );
        Trainer user = trainerRequestDTO.toEntity();
        user.setUserType(UserType.TRAINER);
        trainerRepository.save(user);
        userRepository.save(user);


       /* if (user.getUserType().equals(UserType.TRAINER)) {
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
        }*/

        //moras popravit trainer value u memberima da moze trainer biti null

        return new TrainerDTO(user);
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

    public UserDTO updateAdmin(String id, UserRequestDTO payload){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
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
        System.out.println(memberRequestDTO.getNumOfMonths());
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


        memberRepository.save(member);
        userRepository.save(member);

        if(member!=null && member.getId()!=null){           //if member successfully saved to the database
            int num=memberRequestDTO.getNumOfMonths();
            String memberId=member.getId();
            Membership membership=memberService.createMembershipOnMemberCreation(memberId,num,trainingPlanId);
            membershipRepository.save(membership);
            System.out.println(membership.getEndDate());
        }

        memberRepository.save(member);
        userRepository.save(member);

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
