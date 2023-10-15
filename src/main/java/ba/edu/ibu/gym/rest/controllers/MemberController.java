package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all")
    public List<Member> findAll(){
        return memberService.findAll();
    }
    @GetMapping("/{id}")
    public Member findById(@PathVariable int id){
        return memberService.findById(id);
    }

    @GetMapping("/send-to-all")
    public String sendEmailToAllUsers(@RequestParam String message) {
        return memberService.sendEmailToAllUsers(message);
    }
}
