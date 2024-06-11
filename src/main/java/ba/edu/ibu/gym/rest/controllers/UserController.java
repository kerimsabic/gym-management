package ba.edu.ibu.gym.rest.controllers;


import ba.edu.ibu.gym.core.service.UserService;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
   // @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/admins")
    //@PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<UserDTO>> getAdmins() {
        return ResponseEntity.ok(userService.getUserAdmins());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/members")
    //@PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<UserDTO>> getMembers() {
        return ResponseEntity.ok(userService.getUserMembers());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/trainers")
    //@PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<List<UserDTO>> getTrainers() {
        return ResponseEntity.ok(userService.getUserTrainers());
    }
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    //@PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/token/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN', 'TRAINER')")
    public ResponseEntity<UserDTO> getUserByJToken(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserByToken(id));
    }

   /* @RequestMapping(method = RequestMethod.POST,path = "/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestDTO user){
        return ResponseEntity.ok(userService.addUser(user));
    }*/

    @RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id,@RequestBody UserRequestDTO user){
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return null;
    }

}
