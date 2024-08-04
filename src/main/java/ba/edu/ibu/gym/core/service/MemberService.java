package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.*;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.MembershipRepository;
import ba.edu.ibu.gym.core.repository.TrainerRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    private UserRepository userRepository;
    private UserService userService;

    private TrainingPlanService trainingPlanService;
    private MembershipService membershipService;
    private MembershipRepository membershipRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private  final EmailService emailService ;




    public MemberService(MemberRepository memberRepository, UserRepository userRepository,TrainerService trainerService, TrainerRepository trainerRepository,TrainingPlanService trainingPlanService, MembershipService membershipService,  MembershipRepository membershipRepository, UserService userService, EmailService emailService1 ) {
        this.memberRepository = memberRepository;
        this.userRepository=userRepository;
        this.trainingPlanService=trainingPlanService;
        this.membershipService=membershipService;
        this.membershipRepository=membershipRepository;
        this.userService=userService;
        this.emailService=emailService1;
    }

    public List<MemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members
                .stream()
                .map(MemberDTO::new)
                .collect(toList());
    }

    public List<MemberDTO> getOnlineMembers() {
        List<Member> members = memberRepository.findByStatusTypeAndUserType(StatusType.ONLINE, UserType.MEMBER);
        return members
                .stream()
                .map(MemberDTO::new)
                .collect(toList());
    }
    public List<UserDTO> getOfflineMembers() {
       // List<Member> members = memberRepository.findByStatusType(StatusType.OFFLINE);
        List<User> members=userRepository.findByStatusTypeAndUserType(StatusType.OFFLINE, UserType.MEMBER);
        return members
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    public List<MemberDTO> getMembersPaginated(String search, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Member> members =null ;

        if(search.equals("")){
             members = memberRepository.findAll( pageable);
        }
        else{
             members = memberRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, pageable);
        }

        List<Member> listOfMembers = members.getContent();
        List<MemberDTO>content =listOfMembers.stream().map(MemberDTO::new).collect(toList());
        int totalPages=members.getSize()/pageSize;

        return content;

    }


    public Page<MemberDTO> searchMembers(String keyword, Pageable pageable) {
        Page<Member> members = memberRepository.findByFirstNameContaining(keyword, pageable);
        return members.map(MemberDTO::new);
    }




    public MemberDTO getMemberById(String id){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The member with the given ID does not exist.");
        }
        return new MemberDTO(member.get());
    }


    public Member getMemberById2(String id){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return member.get();
    }



  /*  public void addMemberToTrainer(String memberId, String trainerId){
        Optional<Trainer> trainer = trainerRepository.findById(trainerId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The trainer with the given ID does not exist.");
        }
        Member member=getMemberById2(memberId);
        List<Member> members = trainer.get().getMembers();
        members.add(member);
        trainer.get().setMembers(members);
        trainerRepository.save(trainer.get());
    }*/

    public MemberDTO addMemberToTrainerSpecial(String memberId, String trainerId) {
        Optional<User> trainerOpt = userRepository.findById(trainerId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (trainerOpt.isPresent() && memberOpt.isPresent()) {
            User trainer = trainerOpt.get();
            Member member = memberOpt.get();

            member.setTrainer(trainer);
            memberRepository.save(member);

            return new MemberDTO(member);
        } else {

            throw new ResourceNotFoundException("Member or Trainer not found");
        }
    }


    public MemberDTO removeMemberFromTrainer(String memberId){



        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if ( memberOpt.isPresent()) {
            Member member = memberOpt.get();
             member.setTrainer(null);
            memberRepository.save(member);
            return new MemberDTO(member);
        } else {
            throw new ResourceNotFoundException("Member or Trainer not found");
        }
    }






    public  MemberDTO updateMember(String id, MemberRequestDTO payload){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The member with the given ID does not exist.");
        }
        Member updatedMembers= payload.toEntity();
        String trainerId=payload.getTrainerId();

        TrainingPlan trainingPlan=trainingPlanService.getPlanById(payload.getTrainingPlanId());
        updatedMembers.setTrainingPlan(trainingPlan);

        updatedMembers.setId(member.get().getId());

        updatedMembers.setUserType(UserType.MEMBER);

        if(trainerId!=null){

           /* Trainer newTrainer=trainerService.getTrainerById2(trainerId);
            updatedMembers.setTrainer(newTrainer);
            List<Member> members = new ArrayList<>();
            newTrainer.setMembers(members);*/
        }
        if(payload.getPassword()==null ){
            updatedMembers.setPassword(member.get().getPassword());
        }
        else{
            updatedMembers.setPassword(
                    passwordEncoder.encode(payload.getPassword())
            );
        }


        updatedMembers=memberRepository.save(updatedMembers);

        updateMemberMembership(id, payload);            //for updating the membership


        userRepository.save(updatedMembers);
        return new MemberDTO(updatedMembers);
    }

    public MemberDTO updateMemberPassword(String memberId, PasswordDTO passwordDTO) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<User>userOpt= userRepository.findById(memberId);

        if (memberOpt.isPresent() && userOpt.isPresent()) {
            Member member = memberOpt.get();
            User user = userOpt.get();
            String newPassword = passwordDTO.getPassword();
            String repeatedPassword = passwordDTO.getRepeatedPassword();

            if (newPassword == null || !newPassword.equals(repeatedPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            String encodedPassword = passwordEncoder.encode(newPassword);
            member.setPassword(encodedPassword);
            user.setPassword(encodedPassword);

            memberRepository.save(member);
            userRepository.save(user);
            emailService.sendEmail("kerim.sabic@stu.ibu.edu.ba", member.getFirstName(), "Password change confirmation", "Your password has been successfully changed!", "Password has been changed");

            return new MemberDTO(member);
        } else {
            throw new ResourceNotFoundException("Member not found");
        }
    }


    public void updateMemberMembership(String id, MemberRequestDTO payload){

        Optional<Membership> membership=membershipRepository.findMembershipByMember_Id(id);

        TrainingPlan trainingPlan=trainingPlanService.getPlanById(payload.getTrainingPlanId());
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }


        membership.get().setTrainingPlan(trainingPlan);

        Date startDate = new Date();
        int durationInMonths = payload.getNumOfMonths();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();

        membership.get().setEndDate(endDate);

        Date currentDate = new Date();
        if(endDate.before(currentDate)){
            membership.get().setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.get().setStatusType(StatusType.ONLINE);
        }
        membershipRepository.save(membership.get());

        //new MembershipDTO(membership.get());
    }

    public MembershipDTO updateMemberMembershipWithMembership(String id, MembershipRequestDTO payload){

        Optional<Membership> membership=membershipRepository.findMembershipByMember_Id(id);
        Optional<Member> member = memberRepository.findById(id);


        TrainingPlan trainingPlan=trainingPlanService.getPlanById(payload.getTrainingPlanId());
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }

        membership.get().setTrainingPlan(trainingPlan);

        Date startDate = new Date();
        int durationInMonths = payload.getNumOfMonths();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();

        membership.get().setStartDate(startDate);
        membership.get().setEndDate(endDate);

        Date currentDate = new Date();
        if(endDate.before(currentDate)){
            membership.get().setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.get().setStatusType(StatusType.ONLINE);
        }
        Member updatedmember=member.get();

        updatedmember.setTrainingPlan(trainingPlan);
        memberRepository.save(updatedmember);
        membershipRepository.save(membership.get());

        return new MembershipDTO(membership.get());
    }

    public MembershipDTO renewExistingMembership(String id){

        Optional<Membership> membership = membershipRepository.findMembershipByMember_Id(id);

        Optional<Member> member = memberRepository.findById(id);

        TrainingPlan trainingPlan=trainingPlanService.getPlanById(membership.get().getTrainingPlan().getId());
        if(!membership.isPresent()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }
        membership.get().setTrainingPlan(trainingPlan);

        Date startDate = new Date();

        int durationInMonths = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();
        membership.get().setStartDate(startDate);
        membership.get().setEndDate(endDate);

        Date currentDate = new Date();
        if(endDate.before(currentDate)){
            membership.get().setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.get().setStatusType(StatusType.ONLINE);
        }
        Member updatedmember=member.get();

        updatedmember.setTrainingPlan(trainingPlan);

        memberRepository.save(updatedmember);
        membershipRepository.save(membership.get());



        // Define the desired date format
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy");

        String formattedDate = formatter.format(membership.get().getEndDate());


        emailService.sendEmail("kerim.sabic@stu.ibu.edu.ba", member.get().getFirstName(), "Membership renewal", "Your membership has been renewed! Current plan: "+trainingPlan.getName()+". Exparation date: "+ formattedDate+ " !", "Your membership has been renewed");


        return new MembershipDTO(membership.get());
    }

    public Membership createMembershipOnMemberCreation(String email, int numOfMonths, String trainingPlanId){

        //Member member= getMemberById2(memberId);
        Optional<User> member=userRepository.findByEmail(email);
        System.out.println(member);
        TrainingPlan trainingPlan=trainingPlanService.getPlanById(trainingPlanId);

        Membership membership= new Membership();
        if(member.isPresent()){
            membership.setMember(member.get());
            membership.setTrainingPlan(trainingPlan);
        }



        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, numOfMonths);
        Date endDate = calendar.getTime();

        Date currentDate = new Date();

        if(endDate.before(currentDate)){
            membership.setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.setStatusType(StatusType.ONLINE);
        }

        membership.setStartDate(startDate);
        membership.setEndDate(endDate);


        membershipRepository.save(membership);
        return membership;
    }

    public void deleteMembers(String id){
        Optional<Member> member = memberRepository.findById(id);
        Optional<Membership> membership=membershipRepository.findMembershipByMember_Id(id);
        member.ifPresent(member1 -> {
            memberRepository.delete(member1);
            userRepository.delete(member1);
            membership.ifPresent(membership1 -> {
                membershipRepository.delete(membership1);
            });

        });
    }
}
