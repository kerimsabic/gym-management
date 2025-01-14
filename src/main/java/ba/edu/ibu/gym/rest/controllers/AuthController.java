package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.service.AuthService;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registerTrainer")
    public ResponseEntity<UserDTO> registerTrainer(@RequestBody UserRequestDTO trainer) {
        return ResponseEntity.ok(authService.signUpTrainer(trainer));
    }
    @RequestMapping(method = RequestMethod.POST, path = "/registerMember")
    public ResponseEntity<MemberDTO> registerMember(@RequestBody MemberRequestDTO member) {
        return ResponseEntity.ok(authService.signUpMember(member));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registerAdmin")
    public ResponseEntity<UserDTO> registerAdmin(@RequestBody UserRequestDTO member) {
        return ResponseEntity.ok(authService.signUpAdmin(member));
    }
    @RequestMapping(method = RequestMethod.PUT,path = "/updateAdmin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateAdmin(@PathVariable String id, @RequestBody UserRequestDTO user){
        return ResponseEntity.ok(authService.updateAdmin(id,user));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }
}
