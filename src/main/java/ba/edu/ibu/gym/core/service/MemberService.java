package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member>findAll(){
        return memberRepository.findAll();
    }
    public Member findById(int id){
        return memberRepository.findById(id);
    }
}
