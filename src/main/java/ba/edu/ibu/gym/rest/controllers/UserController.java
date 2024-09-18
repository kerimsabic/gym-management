package ba.edu.ibu.gym.rest.controllers;



import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.core.service.AzureBlobStorageService;
import ba.edu.ibu.gym.core.service.PasswordResetService;
import ba.edu.ibu.gym.core.service.PhotoService;

import ba.edu.ibu.gym.core.service.UserService;
import ba.edu.ibu.gym.rest.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {

    private final UserService userService;

    @Autowired
    private  PasswordResetService passwordResetService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    public static final int SUCCESS = 200; // Success
    private final AzureBlobStorageService azureBlobStorageService;

    public UserController(UserService userService, AzureBlobStorageService azureBlobStorageService) {
        this.userService = userService;
        this.azureBlobStorageService= azureBlobStorageService;
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

    @RequestMapping(method = RequestMethod.PUT, path = "changePasswordUser/{id}")
 //   @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable String id, @RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.ok(passwordResetService.updateUserPassword(id,passwordDTO));
    }


   /* @RequestMapping(method = RequestMethod.POST, path = "/userId/upload-image")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public String uploadImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) throws IOException {
        // Upload the file to S3
        String fileUrl = s3Service.uploadFile(file.getInputStream(), file.getOriginalFilename());

        // Update the user's image URL
        User user = userService.getUserById2(userId);
        userRepository.save(user);

        return fileUrl; // Return the URL of the uploaded image
    }*/


    /*Saving photos*/
    @RequestMapping(method = RequestMethod.POST, path = "/uploadToGoogleDrive/{id}")
    public Object handleFileUpload(@RequestParam("image") MultipartFile file, @PathVariable String id) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "FIle is empty";
        }
        File tempFile = File.createTempFile("IMG", null);
        file.transferTo(tempFile);
        ImageDTO res = photoService.uploadImageToDrive(tempFile, id);
        System.out.println(res);
        return res;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/uploadToGoogleDriveEmail/{email}")
    public Object handleFileUploadWithoudUserId(@RequestParam("image") MultipartFile file, @PathVariable String email) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("IMG", null);
        file.transferTo(tempFile);
        ImageDTO res = photoService.uploadImageToDriveWithoudUserId(tempFile, email);
        System.out.println(res);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/uploadImage")
    public Object uploadImage(@RequestParam("image") MultipartFile file ) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("IMG", null);
        file.transferTo(tempFile);
        ImageDTO res = photoService.uploadImage(tempFile);
        System.out.println(res);
        return res;
    }

    @PostMapping("/uploadImageAzure")
    public ResponseEntity<ImageUploadResponse> uploadImageAzure(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = azureBlobStorageService.uploadImage(file);

            // Create and return a successful response
            ImageUploadResponse response = new ImageUploadResponse(true, "Image uploaded successfully", imageUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ImageUploadResponse response = new ImageUploadResponse(false, "Image upload failed: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/uploadImageAzure/{memberId}")
    public ResponseEntity<ImageUploadResponse> uploadImageAzureWithMemberId(@RequestParam("image") MultipartFile file, @PathVariable String memberId) {
        try {
            String imageUrl = azureBlobStorageService.uploadImageAzureWithMemberId(file, memberId);

            // Create and return a successful response
            ImageUploadResponse response = new ImageUploadResponse(true, "Image uploaded successfully", imageUrl);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            // Create and return an error response
            ImageUploadResponse response = new ImageUploadResponse(false, "Image upload failed: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}


