package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.service.MemberService;
import ba.edu.ibu.gym.rest.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/members")
@SecurityRequirement(name = "JWT Security")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
   // @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<MemberDTO>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());

    }
    @RequestMapping(method = RequestMethod.GET, path = "/online")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<MemberDTO>> getOnlineMembers() {
        return ResponseEntity.ok(memberService.getOnlineMembers());

    }
    @RequestMapping(method = RequestMethod.GET, path = "/offline")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<UserDTO>> getOfflineMembers() {
        return ResponseEntity.ok(memberService.getOfflineMembers());

    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pagination/")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<List<MemberDTO>> getAdmins(@RequestParam(defaultValue = "") String search,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int pageSize) {
        return ResponseEntity.ok(memberService.getMembersPaginated( search,page, pageSize));
    }


    /*@RequestMapping(method = RequestMethod.POST,path = "/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberRequestDTO member){
        return ResponseEntity.ok(memberService.addMember(member));
    }*/
    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable String id,@RequestBody MemberRequestDTO member){
        return ResponseEntity.ok(memberService.updateMember(id,member));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "changePassword/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> updateMemberPassword(@PathVariable String id, @RequestBody PasswordDTO passwordDTO) {
      /*  String newPassword = passwords.get("password");
        String repeatedPassword = passwords.get("repeatedPassword");*/

        return ResponseEntity.ok(memberService.updateMemberPassword(id,passwordDTO));
    }

    /*@RequestMapping(method = RequestMethod.PUT,path = "/password/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> updateMemberPassword(@PathVariable String id,@RequestBody MemberPasswordRequestDTO password){
        return ResponseEntity.ok(memberService.updateMemberPassword(id,password));
    }*/

    @RequestMapping(method = RequestMethod.PUT,path = "/membership/{id}")
   // @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MembershipDTO> updateMemberMembershipSpecial(@PathVariable String id, @RequestBody MembershipRequestDTO payload){
        return ResponseEntity.ok(memberService.updateMemberMembershipWithMembership(id,payload));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/renewMembership/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MembershipDTO> renewExistingMembership(@PathVariable String id){
        return ResponseEntity.ok(memberService.renewExistingMembership(id));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable String id){
        memberService.deleteMembers(id);
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT,path = "setTrainer/{memberId}/{trainerId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> addMembertToTrainerSpecial(@PathVariable String memberId,@PathVariable String trainerId){
        return ResponseEntity.ok(memberService.addMemberToTrainerSpecial(memberId,trainerId));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "removeTrainer/{memberId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDTO> removeMemberFromTrainer(@PathVariable String memberId){
        return ResponseEntity.ok(memberService.removeMemberFromTrainer(memberId));
    }



// ovo mi ne treba za sada
    @RequestMapping(method = RequestMethod.GET,path = "GETMEMBERS2/")
    public Page<MemberDTO> getMembers2(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return memberService.searchMembers(keyword, pageable);
    }
}
