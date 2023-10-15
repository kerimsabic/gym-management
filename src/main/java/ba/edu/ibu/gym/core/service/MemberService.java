package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.api.mailsender.MailSender;
import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

   /* The first method of implemetation, need to comment out conditional property in both senders
    @Autowired
    private MailSender mailgunSender;
    @Autowired
    private MailSender sendgridSender;  */

    @Autowired
    private MailSender mailSender;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member>findAll(){
        return memberRepository.findAll();
    }
    public Member findById(int id){
        return memberRepository.findById(id);
    }

    public String sendEmailToAllUsers(String message) {
        List<Member> members = memberRepository.findAll();
        return mailSender.send(members, message);
    }
}
