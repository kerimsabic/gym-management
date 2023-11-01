package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private TrainerService trainerService;
    private UserRepository userRepository;

  //  public MemberService(){}

    public MemberService(MemberRepository memberRepository, UserRepository userRepository,TrainerService trainerService) {
        this.memberRepository = memberRepository;
        this.userRepository=userRepository;
        this.trainerService=trainerService;
    }

    public List<MemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();


        return members
                .stream()
                .map(MemberDTO::new)
                .collect(toList());
    }
    //ova funkcija mi ne vraca podatke od trenera

/*
    public List<MemberDTO> getMembersWithTrainers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> membersWithTrainers = new ArrayList<>();

        for (Member member : members) {
            MemberDTO memberDTO = new MemberDTO(member);
            memberDTO.setId(member.getId());
            memberDTO.setFirstName(member.getFirstName());
            memberDTO.setLastName(member.getLastName());
            memberDTO.setEmail(member.getEmail());
            memberDTO.setImage(member.getImage());
            memberDTO.setQrCode(member.getQrCode());

            // Retrieve the associated Trainer using the reference in the Member document
            TrainerDTO trainer = member.getTrainer();
            memberDTO.setTrainer(trainer);

            membersWithTrainers.add(memberDTO);
        }

        return membersWithTrainers;
    }*/




    public MemberDTO getMemberById(String id){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new MemberDTO(member.get());
    }

    /*
    public Member getMemberById2(String id){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return member.get();
    }*/


   /* public MemberDTO addMember(MemberRequestDTO payload, Trainer trainer) {
        String trainerId=payload.getTrainerId();
        TrainerDTO newTrainer=trainerService.getTrainerById(trainerId);

        Member member = memberRepository.save(payload.toEntity());
        return new MemberDTO(member);
    }*/



     public MemberDTO addMember(MemberRequestDTO payload) {

        String trainerId=payload.getTrainerId();
        Member member = payload.toEntity();

     //   memberRepository.save(payload.toEntity());
         if (trainerId != null) {

             //ovje mi izbacuje i sifru i sve jer je Trainer a ne TrainerDTO
             Trainer newTrainer=trainerService.getTrainerById2(trainerId);
             member.setTrainer(newTrainer);

           /*  List<MemberDTO> members = new ArrayList<>();
            // members.add(new MemberDTO(member));// da doda membera i trainer members listu
             newTrainer.setMembers(members);*/


             memberRepository.save(member);
         }


         memberRepository.save(member);

         userRepository.save(member);
        return new MemberDTO(member);
    }




    public  MemberDTO updateMember(String id, MemberRequestDTO payload){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        Member updatedMembers= payload.toEntity();
        String trainerId=payload.getTrainerId();

        updatedMembers.setId(member.get().getId());

        if(trainerId!=null){
            //ovje mi izbacuje i sifru i sve jer je Trainer a ne TrainerDTO
            Trainer newTrainer=trainerService.getTrainerById2(trainerId);
            updatedMembers.setTrainer(newTrainer);
            List<MemberDTO> members = new ArrayList<>();
            newTrainer.setMembers(members);
        }

        updatedMembers=memberRepository.save(updatedMembers);


        userRepository.save(updatedMembers);
        return new MemberDTO(updatedMembers);
    }

    public void deleteMembers(String id){
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(member1 -> {
            memberRepository.delete(member1);
            userRepository.delete(member1);
        });
    }
}
