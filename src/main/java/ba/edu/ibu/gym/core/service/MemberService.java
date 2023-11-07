package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.TrainerRepository;
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
    private TrainerRepository trainerRepository;



    public MemberService(MemberRepository memberRepository, UserRepository userRepository,TrainerService trainerService, TrainerRepository trainerRepository) {
        this.memberRepository = memberRepository;
        this.userRepository=userRepository;
        this.trainerService=trainerService;
        this.trainerRepository=trainerRepository;
    }

    public List<MemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();


        return members
                .stream()
                .map(MemberDTO::new)
                .collect(toList());
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


   /* public MemberDTO addMember(MemberRequestDTO payload, Trainer trainer) {
        String trainerId=payload.getTrainerId();
        TrainerDTO newTrainer=trainerService.getTrainerById(trainerId);

        Member member = memberRepository.save(payload.toEntity());
        return new MemberDTO(member);
    }*/


    public void addMemberToTrainer(String memberId, String trainerId){
        Optional<Trainer> trainer = trainerRepository.findById(trainerId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The trainer with the given ID does not exist.");
        }
        MemberDTO member=getMemberById(memberId);
        List<MemberDTO> members = trainer.get().getMembers();
        members.add(member);
        trainer.get().setMembers(members);
       // return new TrainerDTO(trainer.get());
    }



     public MemberDTO addMember(MemberRequestDTO payload) {

        String trainerId=payload.getTrainerId();
        Member member = payload.toEntity();
        String memberId= payload.getTrainerId();

        member.setUserType(UserType.MEMBER);

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

        // addMemberToTrainer(memberId,trainerId );

        return new MemberDTO(member);

    }




    public  MemberDTO updateMember(String id, MemberRequestDTO payload){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The member with the given ID does not exist.");
        }
        Member updatedMembers= payload.toEntity();
        String trainerId=payload.getTrainerId();

        updatedMembers.setId(member.get().getId());

        updatedMembers.setUserType(UserType.MEMBER);

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
