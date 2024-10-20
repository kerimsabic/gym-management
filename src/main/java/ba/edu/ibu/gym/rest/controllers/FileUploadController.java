package ba.edu.ibu.gym.rest.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    private static final String UPLOADED_FOLDER = "src/main/java/ba/edu/ibu/gym/images";

    @PostMapping("/upload")
    public @ResponseBody String singleFileUpload(@RequestParam("file") MultipartFile file,
                                                 RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // Save the file to the server
            File fileToSave = new File(UPLOADED_FOLDER + file.getOriginalFilename());
            file.transferTo(fileToSave);

            // TODO: Update user's profile photo path in the database

            return "You successfully uploaded '" + file.getOriginalFilename() + "'";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload '" + file.getOriginalFilename() + "'";
        }
    }
}