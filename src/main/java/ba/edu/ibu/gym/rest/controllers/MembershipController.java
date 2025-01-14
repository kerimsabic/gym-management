package ba.edu.ibu.gym.rest.controllers;


import ba.edu.ibu.gym.core.service.MemberService;
import ba.edu.ibu.gym.core.service.MembershipService;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import ba.edu.ibu.gym.rest.dto.MembershipRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/memberships")
@SecurityRequirement(name = "JWT Security")
public class MembershipController {

    private MembershipService membershipService;
    private MemberService memberService;

    public MembershipController(MembershipService membershipService, MemberService memberService){
        this.membershipService=membershipService;
        this.memberService=memberService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MembershipDTO>> getMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MembershipDTO> getMembershipById(@PathVariable String id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }
    @RequestMapping(method = RequestMethod.GET, path = "/member/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<MembershipDTO> getMembershipByMemberId(@PathVariable String id) {
        return ResponseEntity.ok(membershipService.getMembershipByMemberId(id));
    }

   /* @RequestMapping(method = RequestMethod.POST,path = "/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MembershipDTO> addMembership(@RequestBody MembershipRequestDTO payload){
        return ResponseEntity.ok(membershipService.createMembership(payload));
    }*/

   /* @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MembershipDTO> updateMembership(@PathVariable String id,@RequestBody MembershipRequestDTO payload){
        return ResponseEntity.ok(memberService.updateMemberMembership(id,payload));
    }*/

    /*@RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<MembershipDTO> updateMembership(@PathVariable String id,@RequestBody MembershipRequestDTO membership){
        return ResponseEntity.ok(membershipService.updateMembership(id,membership));
    }*/

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMembership(@PathVariable String id){
        membershipService.deleteMembership(id);
        return null;
    }
}
