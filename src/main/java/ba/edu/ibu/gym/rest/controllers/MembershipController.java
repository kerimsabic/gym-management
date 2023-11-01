package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Membership;
import ba.edu.ibu.gym.core.service.MembershipService;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import ba.edu.ibu.gym.rest.dto.MembershipRequestDTO;
import ba.edu.ibu.gym.rest.dto.TrainerDTO;
import ba.edu.ibu.gym.rest.dto.TrainerRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private MembershipService membershipService;

    public MembershipController(MembershipService membershipService){
        this.membershipService=membershipService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<MembershipDTO>> getMemberships() {
        return ResponseEntity.ok(membershipService.getMemebrships());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<MembershipDTO> getMembershipById(@PathVariable String id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }
    @RequestMapping(method = RequestMethod.POST,path = "/register")
    public ResponseEntity<MembershipDTO> addMembership(@RequestBody MembershipRequestDTO membership){
        return ResponseEntity.ok(membershipService.createMembership(membership));
    }
    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<MembershipDTO> updateMembership(@PathVariable String id,@RequestBody MembershipRequestDTO membership){
        return ResponseEntity.ok(membershipService.updateMembership(id,membership));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable String id){
        membershipService.deleteMembership(id);
        return null;
    }
}