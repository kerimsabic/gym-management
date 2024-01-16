package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.service.AuthService;
import ba.edu.ibu.gym.rest.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<TrainerDTO> register(@RequestBody TrainerRequestDTO user) {
        return ResponseEntity.ok(authService.signUp(user));
    }
    @RequestMapping(method = RequestMethod.POST, path = "/registerMember")
    public ResponseEntity<MemberDTO> registerMember(@RequestBody MemberRequestDTO member) {
        return ResponseEntity.ok(authService.signUpMember(member));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registerAdmin")
    public ResponseEntity<UserDTO> registerAdmin(@RequestBody UserRequestDTO member) {
        return ResponseEntity.ok(authService.signUpAdmin(member));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }
}
