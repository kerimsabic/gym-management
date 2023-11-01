package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.TrainerRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TrainerService {
    private TrainerRepository trainerRepository;
    private UserRepository userRepository;




    public TrainerService(TrainerRepository trainerRepository,UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository=userRepository;

    }

    public List<TrainerDTO> getTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers
                .stream()
                .map(TrainerDTO::new)
                .collect(toList());
    }

    public TrainerDTO getTrainerById(String id){
        Optional<Trainer> trainer = trainerRepository.findById(id);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new TrainerDTO(trainer.get());
    }

    public Trainer getTrainerById2(String id){
        Optional<Trainer> trainer = trainerRepository.findById(id);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return trainer.get();
    }



  /*  public TrainerDTO addMemberToTrainer(String memberId,String traienrId){
        Optional<Trainer> trainer = trainerRepository.findById(traienrId);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        MemberDTO member=memberService.getMemberById(memberId);
        List<MemberDTO> members = new ArrayList<>();
        trainer.get().setMembers(members);
        return new TrainerDTO(trainer.get());
    }*/ //ovo sam uradio u members servisu kada se dodaje novi member



    public TrainerDTO addTrainer(TrainerRequestDTO payload) {
        Trainer trainer = trainerRepository.save(payload.toEntity());
        userRepository.save(trainer);
        return new TrainerDTO(trainer);
    }

    public  TrainerDTO updateTrainer(String id, TrainerRequestDTO payload){
        Optional<Trainer> trainer = trainerRepository.findById(id);
        if(trainer.isEmpty()){
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        Trainer updatedTrainer= payload.toEntity();
        updatedTrainer.setId(trainer.get().getId());
        updatedTrainer=trainerRepository.save(updatedTrainer);
        userRepository.save(updatedTrainer);
        return new TrainerDTO(updatedTrainer);
    }

    public void deleteTrainer(String id){

        Optional<Trainer> trainer = trainerRepository.findById(id);
        trainer.ifPresent(trainer1 -> {
            trainerRepository.delete(trainer1);
            userRepository.delete(trainer1);
        });

    }
}
