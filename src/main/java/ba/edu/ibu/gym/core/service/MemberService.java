package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.model.enums.StatusType;
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
    private TrainingPlanService trainingPlanService;



    public MemberService(MemberRepository memberRepository, UserRepository userRepository,TrainerService trainerService, TrainerRepository trainerRepository,TrainingPlanService trainingPlanService) {
        this.memberRepository = memberRepository;
        this.userRepository=userRepository;
        this.trainerService=trainerService;
        this.trainerRepository=trainerRepository;
        this.trainingPlanService=trainingPlanService;
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



    public void addMemberToTrainer(String memberId, String trainerId){
        Optional<Trainer> trainer = trainerRepository.findById(trainerId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The trainer with the given ID does not exist.");
        }
        Member member=getMemberById2(memberId);
        List<Member> members = trainer.get().getMembers();
        members.add(member);
        trainer.get().setMembers(members);
        trainerRepository.save(trainer.get());
    }

    public MemberDTO addMemberToTrainerSpecial(String memberId, String trainerId){

        Optional<Trainer> trainer = trainerRepository.findById(trainerId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The trainer with the given ID does not exist.");
        }
        Member member=getMemberById2(memberId);
        List<Member> members = trainer.get().getMembers();
        members.add(member);
        trainer.get().setMembers(members);
        trainerRepository.save(trainer.get());

        Trainer newTrainer= trainerService.getTrainerById2(trainerId);
        member.setTrainer(newTrainer);

        memberRepository.save(member);
        return new MemberDTO(member);
    }

    public MemberDTO removeMemberFromTrainer(String memberId, String trainerId){

        Optional<Trainer> trainer = trainerRepository.findById(trainerId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The trainer with the given ID does not exist.");
        }

        Member member=getMemberById2(memberId);

        Trainer newTrainer= trainer.get();

        List<Member> members = newTrainer.getMembers();
        newTrainer.removeMember(member);
       // members.remove(member);


        //trainer.get().setMembers(members);
       // trainerRepository.save(trainer.get());

        newTrainer.setMembers(members);
        member.setTrainer(null);

        trainerRepository.save(newTrainer);
        memberRepository.save(member);
        return new MemberDTO(member);
    }



     public MemberDTO addMember(MemberRequestDTO payload) {

        String trainerId=payload.getTrainerId();
        Member member = payload.toEntity();
        String trainingPlanId= payload.getTrainingPlanId();
         System.out.println(payload.getTrainingPlanId()+"   "+ payload.getTrainerId());


        member.setUserType(UserType.MEMBER);
        //member.setStatusType(StatusType.ONLINE);

         if (trainerId != null) {

             Trainer newTrainer=trainerService.getTrainerById2(trainerId);
             member.setTrainer(newTrainer);

             Member member2=memberRepository.save(member);
             addMemberToTrainer(member2.getId(),trainerId );

         }
         TrainingPlan trainingPlan=trainingPlanService.getPlanById(trainingPlanId);
         member.setTrainingPlan(trainingPlan);

         memberRepository.save(member);

         userRepository.save(member);


        /* Member member2=memberRepository.save(member);
         addMemberToTrainer(member2.getId(),trainerId );*/

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

            Trainer newTrainer=trainerService.getTrainerById2(trainerId);
            updatedMembers.setTrainer(newTrainer);
            List<Member> members = new ArrayList<>();
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
