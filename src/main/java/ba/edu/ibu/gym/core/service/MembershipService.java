package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.MembershipRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class MembershipService {
    private  MembershipRepository membershipRepository;
    private MemberService memberService;

    public MembershipService(MembershipRepository membershipRepository, MemberService memberService) {
        this.membershipRepository = membershipRepository;
        this.memberService=memberService;
    }

    public List<MembershipDTO> getMemebrships() {
        List<Membership> memberships = membershipRepository.findAll();
        return memberships
                .stream()
                .map(MembershipDTO::new)
                .collect(toList());

    }

    public MembershipDTO getMembershipById(String id){
        Optional<Membership> membership = membershipRepository.findById(id);
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new MembershipDTO(membership.get());
    }





/*
    public Membership addMembership(MembershipRequestDTO payload) {
        String memberId=payload.getMemberId();

        if (memberId != null) {
            MemberDTO newMember=memberService.getMemberById(memberId);
            setTrainer(newTrainer);
            System.out.println("Hello");

            List<MemberDTO> members = new ArrayList<>();
            // members.add(new MemberDTO(member));// da doda membera i trainer members listu
            newTrainer.setMembers(members);

            System.out.println(member.getTrainer());

            memberRepository.save(member);
        }

        Membership membership = membershipRepository.save(payload.toEntity());
        membershipRepository.save(membership);
        return membership;
    }*/



    /*
public MembershipDTO createMembership(MembershipRequestDTO membershipRequestDTO) {
    Membership membership = new Membership();
    membership=membershipRequestDTO.toEntity();
    String memberId=membershipRequestDTO.getMemberId();

    if(memberId!=null){
        MemberDTO member=memberService.getMemberById(memberId);
        membership.setMember(member);
        membershipRepository.save(membership);
    }
    else{
        throw  new ResourceNotFoundException("The user with the given ID does not exist.");
    }
   // Membership membership = membershipRepository.save(membershipRequestDTO.toEntity());
    return new MembershipDTO(membership);
}*/




    public MembershipDTO createMembership(MembershipRequestDTO membershipRequestDTO) {

        String memberId=membershipRequestDTO.getMemberId();
        Membership membership = new Membership();


        MemberDTO member=memberService.getMemberById(memberId);

        Date startDate = membershipRequestDTO.getStartDate();
        int durationInMonths = membershipRequestDTO.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, durationInMonths);
        Date endDate = calendar.getTime();

        membership.setMember(member);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);

        membershipRepository.save(membership);
        return new MembershipDTO(membership);
    }


    public  MembershipDTO updateMembership(String id, MembershipRequestDTO payload){
        Optional<Membership> membership = membershipRepository.findById(id);
        if(membership.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        Membership updatedMembership= payload.toEntity();
        updatedMembership.setId(membership.get().getId());
        updatedMembership=membershipRepository.save(updatedMembership);
        return new MembershipDTO(updatedMembership);
    }

    public void deleteMembership(String id){

        Optional<Membership> membership = membershipRepository.findById(id);
        membership.ifPresent(membershipRepository::delete);

    }
}
