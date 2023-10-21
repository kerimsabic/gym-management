package ba.edu.ibu.gym.rest.controllers;


import ba.edu.ibu.gym.core.service.UserService;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @RequestMapping(method = RequestMethod.POST,path = "/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestDTO user){
        return ResponseEntity.ok(userService.addUser(user));
    }
}
