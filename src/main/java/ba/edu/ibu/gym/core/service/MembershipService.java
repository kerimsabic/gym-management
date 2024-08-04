package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.repository.MembershipRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MembershipService {

    private MembershipRepository membershipRepository;

    private TrainingPlanService trainingPlanService;

    public MembershipService(MembershipRepository membershipRepository, TrainingPlanService trainingPlanService){
        this.membershipRepository=membershipRepository;

        this.trainingPlanService=trainingPlanService;

    }


    public List<MembershipDTO> getAllMemberships(){
        List<Membership> memberships = membershipRepository.findAll();
        Date currentDate = new Date();
        for (Membership membership:
            memberships ) {
            if (membership.getEndDate().before(currentDate)){
                membership.setStatusType(StatusType.OFFLINE);
                membershipRepository.save(membership);
            }
        }

        return memberships
                .stream()
                .map(MembershipDTO::new)
                .collect(toList());
    }

    public MembershipDTO getMembershipById(String id){
        Optional<Membership> membership = membershipRepository.findById(id);
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }

        /*Date startDate = new Date();
        int durationInMonths = payload.getNumOfMonths();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();

        membership.get().setEndDate(endDate);*/

        Date currentDate = new Date();
        if(membership.get().getEndDate().before(currentDate)){
            membership.get().setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.get().setStatusType(StatusType.ONLINE);
        }
        membershipRepository.save(membership.get());


        return new MembershipDTO(membership.get());
    }
    public MembershipDTO getMembershipByMemberId(String id){
        Optional<Membership> membership = membershipRepository.findMembershipByMember_Id(id);
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }
        return new MembershipDTO(membership.get());
    }

    /*public MembershipDTO updateMemberMembership(String id, MembershipRequestDTO payload){

        Optional<Membership> membership=membershipRepository.findById(id);
        Member member= memberService.getMemberById2(payload.getMemberId());
        TrainingPlan trainingPlan=trainingPlanService.getPlanById(payload.getTrainingPlanId());
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The membership with the given ID does not exist.");
        }

        membership.get().setMember(member);
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

        return new MembershipDTO(membership.get());
    }*/

   /* public MembershipDTO createMembership(MembershipRequestDTO payload){

        String memberId= payload.getMemberId();
        String trainigPlanId=payload.getTrainingPlanId();

        Member member= memberService.getMemberById2(memberId);
        TrainingPlan trainingPlan=trainingPlanService.getPlanById(trainigPlanId);

        Membership membership= payload.toEntity();

        membership.setMember(member);
        membership.setTrainingPlan(trainingPlan);


        Date startDate = new Date();
        int durationInMonths = payload.getNumOfMonths();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);                            //used to calculate the end date based on provided number of months
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();

        Date currentDate = new Date();

        if(endDate.before(currentDate)){
            membership.setStatusType(StatusType.OFFLINE);
        }
        else{
            membership.setStatusType(StatusType.ONLINE);
        }

        membership.setMember(member);
        membership.setTrainingPlan(trainingPlan);
        membership.setEndDate(endDate);

        membershipRepository.save(membership);
        return new MembershipDTO(membership);
    }*/

    /*public Membership createMembershipOnMemberCreation(String memberId, int numOfMonths, String trainingPlanId){

        Member member= memberService.getMemberById2(memberId);
        TrainingPlan trainingPlan=trainingPlanService.getPlanById(trainingPlanId);

        Membership membership= new Membership();

        membership.setMember(member);
        membership.setTrainingPlan(trainingPlan);


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
    }*/


    public void deleteMembership(String id) {
        Optional<Membership> membership = membershipRepository.findById(id);
        membership.ifPresent(membershipRepository::delete);

    }

}
