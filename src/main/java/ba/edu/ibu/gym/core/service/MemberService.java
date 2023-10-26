package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private TrainerService trainerService;

    public MemberService(){}

    public MemberService(MemberRepository memberRepository,TrainerService trainerService) {
        this.memberRepository = memberRepository;
        this.trainerService=trainerService;
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
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new MemberDTO(member.get());
    }

    public MemberDTO addMember(MemberRequestDTO payload, Trainer trainer) {
        String trainerId=payload.getTrainerId();
        TrainerDTO newTrainer=trainerService.getTrainerById(trainerId);

        Member member = memberRepository.save(payload.toEntity());
        return new MemberDTO(member);
    }

    public  UserDTO updateUser(String id, MemberRequestDTO payload){
        Optional<Member> member = memberRepository.findById(id);
        if(member.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        Member updatedMembers= payload.toEntity();
        updatedMembers.setId(member.get().getId());
        updatedMembers=memberRepository.save(updatedMembers);
        return new UserDTO(updatedMembers);
    }

    public void deleteMembers(String id){
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(memberRepository::delete);
    }
}
