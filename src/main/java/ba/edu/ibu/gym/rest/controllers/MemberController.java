package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.service.MemberService;
import ba.edu.ibu.gym.rest.dto.MemberDTO;
import ba.edu.ibu.gym.rest.dto.MemberRequestDTO;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import ba.edu.ibu.gym.rest.dto.TrainerRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/members")
@SecurityRequirement(name = "JWT Security")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<MemberDTO>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());

    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }


    @RequestMapping(method = RequestMethod.POST,path = "/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberRequestDTO member){
        return ResponseEntity.ok(memberService.addMember(member));
    }
    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable String id,@RequestBody MemberRequestDTO member){
        return ResponseEntity.ok(memberService.updateMember(id,member));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable String id){
        memberService.deleteMembers(id);
        return null;
    }

  /*  @RequestMapping(method = RequestMethod.PUT,path = "/{id}/{id2}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> addMembertToTrainerSpecial(@RequestParam String memberId,@RequestParam String trainerId){
        return ResponseEntity.ok(memberService.addMemberToTrainerSpecial(memberId,trainerId));
    }*/
}
