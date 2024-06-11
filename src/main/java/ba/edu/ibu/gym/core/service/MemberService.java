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




    public MemberService(MemberRepository memberRepository, UserRepository userRepository,TrainerService trainerService, TrainerRepository trainerRepository,TrainingPlanService trainingPlanService, MembershipService membershipService,  MembershipRepository membershipRepository, UserService userService ) {
        this.memberRepository = memberRepository;
        this.userRepository=userRepository;
        this.trainingPlanService=trainingPlanService;
        this.membershipService=membershipService;
        this.membershipRepository=membershipRepository;
        this.userService=userService;
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

    public MemberDTO addMemberToTrainerSpecial(String memberId, String trainerId){
        User trainer = userService.getUserById2(trainerId);
        Member member=getMemberById2(memberId);

        member.setTrainer(trainer);

        memberRepository.save(member);
        userRepository.save(member);
        return new MemberDTO(member);
    }

    public MemberDTO removeMemberFromTrainer(String memberId){


        Member member=getMemberById2(memberId);


        member.setTrainer(null);

        memberRepository.save(member);
        userRepository.save(member);
        return new MemberDTO(member);
    }



    /* public MemberDTO addMember(MemberRequestDTO payload) {

        String trainerId=payload.getTrainerId();
        Member member = payload.toEntity();
        String trainingPlanId= payload.getTrainingPlanId();
         System.out.println(payload.getTrainingPlanId()+"   "+ payload.getTrainerId());


        member.setUserType(UserType.MEMBER);
        //member.setStatusType(StatusType.ONLINE);

         if (trainerId != null) {

            // Trainer newTrainer=trainerService.getTrainerById2(trainerId);
            // member.setTrainer(newTrainer);

             Member member2=memberRepository.save(member);
            // addMemberToTrainer(member2.getId(),trainerId );

         }
         TrainingPlan trainingPlan=trainingPlanService.getPlanById(trainingPlanId);
         member.setTrainingPlan(trainingPlan);

         memberRepository.save(member);

         userRepository.save(member);


         Member member2=memberRepository.save(member);
         addMemberToTrainer(member2.getId(),trainerId );

        return new MemberDTO(member);

    }*/


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

   /* public  MemberDTO updateMemberPassword(String id, MemberPasswordRequestDTO newpassword){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The member with the given ID does not exist.");
        }
        String newPassword=newpassword.toEntity();

        member.get().setPassword(
                passwordEncoder.encode(newPassword)
        );
        System.out.println(member.get().getPassword());
        Member updatedMember=member.get();
        updatedMember.setPassword(member.get().getPassword());
        memberRepository.save(updatedMember);
        userRepository.save(updatedMember);
        return new MemberDTO(updatedMember);
    }*/

    public MemberDTO updateMemberPassword(String id, String password, String repeatedPassword) {
        if (!Objects.equals(password, repeatedPassword)) {
            throw new ResourceNotFoundException("Passwords do not match");
        }

        Optional<Member> memberOptional = memberRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(id);

        if (memberOptional.isEmpty() && userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The member with the given ID does not exist.");
        }

        Member member = memberOptional.get();
        User user = userOptional.get();

        String encodedPassword = passwordEncoder.encode(password);
        member.setPassword(encodedPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
        memberRepository.save(member);

        return new MemberDTO(member);
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
        if(membership.isEmpty()){
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
       // User updatedUser=user.get();

        updatedmember.setTrainingPlan(trainingPlan);

        memberRepository.save(updatedmember);
        membershipRepository.save(membership.get());

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
