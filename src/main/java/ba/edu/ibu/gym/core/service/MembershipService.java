package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.MembershipRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MembershipService {

    private MembershipRepository membershipRepository;
    private MemberService memberService;
    private TrainingPlanService trainingPlanService;

    public MembershipService(MembershipRepository membershipRepository, MemberService memberService, TrainingPlanService trainingPlanService){
        this.membershipRepository=membershipRepository;
        this.memberService=memberService;
        this.trainingPlanService=trainingPlanService;

    }


    public List<MembershipDTO> getAllMemberships(){
        List<Membership> memberships = membershipRepository.findAll();
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
        return new MembershipDTO(membership.get());
    }

    public MembershipDTO createMembership(MembershipRequestDTO payload){

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

        membership.setMember(member);
        membership.setTrainingPlan(trainingPlan);
        membership.setEndDate(endDate);

        membershipRepository.save(membership);
        return new MembershipDTO(membership);
    }


    public void deleteMembership(String id) {
        Optional<Membership> membership = membershipRepository.findById(id);
        membership.ifPresent(membershipRepository::delete);

    }

}
