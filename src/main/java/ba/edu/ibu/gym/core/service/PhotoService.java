package ba.edu.ibu.gym.core.service;


import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import ba.edu.ibu.gym.rest.dto.ImageDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
public class PhotoService {

    private static final JsonFactory JSON_FACTORY= GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH= getPathToGoogleCredentials();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;


    private  static String getPathToGoogleCredentials(){
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "src\\main\\resources\\photosCreds.json");
        return filePath.toString();

    }

    public ImageDTO uploadImageToDriveWithoudUserId(File file, String email) throws GeneralSecurityException, IOException {
        ImageDTO res = new ImageDTO();

        try{
            String folderId = "1jcC1BOH4Mc5O8Q5TNyZAKm1zWjzwG4LV";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);

            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isEmpty()){
                throw new IllegalArgumentException("no such user");
            }
            User user = userOptional.get();
            if(user.getUserType().equals(UserType.MEMBER)){
                Optional<Member> member= memberRepository.findByEmail(email);
                member.get().setImage(imageUrl);
                memberRepository.save(member.get());
            }
            user.setImage(imageUrl);
            userRepository.save(user);

        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return  res;

    }

    public ImageDTO uploadImageToDrive(File file, String userId) throws GeneralSecurityException, IOException {
        ImageDTO res = new ImageDTO();

        try{
            String folderId = "1jcC1BOH4Mc5O8Q5TNyZAKm1zWjzwG4LV";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);

            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isEmpty()){
                throw new IllegalArgumentException("no such user");
            }
            User user = userOptional.get();
            if(user.getUserType().equals(UserType.MEMBER)){
                Optional<Member> member= memberRepository.findById(userId);
                member.get().setImage(imageUrl);
                memberRepository.save(member.get());
            }
            user.setImage(imageUrl);
            userRepository.save(user);



        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return  res;

    }

    public ImageDTO uploadImage(File file) throws GeneralSecurityException, IOException {
        ImageDTO res = new ImageDTO();

        try{
            String folderId = "1jcC1BOH4Mc5O8Q5TNyZAKm1zWjzwG4LV";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);

        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return  res;

    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }


}
