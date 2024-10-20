package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.UserRepository;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class AzureBlobStorageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Value("${AZURE_BLOB_SERVICE_ENDPOINT}")
    private String blobServiceEndpoint;

    @Value("${AZURE_BLOB_SAS_TOKEN}")
    private String sasToken;

    @Value("${AZURE_BLOB_CONTAINER_NAME}")
    private String containerName;

    private BlobContainerClient getBlobContainerClient() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(blobServiceEndpoint + "?" + sasToken)
                .buildClient();

        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        BlobContainerClient containerClient = getBlobContainerClient();

        if (!containerClient.exists()) {
            containerClient.create();
        }

        // Generate a unique name for the file using UUID
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        InputStream fileInputStream = file.getInputStream();

        // Upload the file
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint(containerClient.getBlobContainerUrl() + "/" + uniqueFileName + "?" + sasToken)
                .buildClient();

        blobClient.upload(fileInputStream, file.getSize(), true);

        // Set the content type
        BlobHttpHeaders headers = new BlobHttpHeaders();
        headers.setContentType(file.getContentType());
        blobClient.setHttpHeaders(headers);

        String imageUrl = containerClient.getBlobContainerUrl() + "/" + uniqueFileName;

        // Update the user's image URL
       /* Optional<User> userOptional = userRepository.findById(userId);
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
        userRepository.save(user);*/


        return imageUrl;
    }

    public String uploadImageAzureWithMemberId(MultipartFile file, String userId) throws IOException {
        BlobContainerClient containerClient = getBlobContainerClient();

        if (!containerClient.exists()) {
            containerClient.create();
        }

        // Generate a unique name for the file using UUID
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        InputStream fileInputStream = file.getInputStream();

        // Upload the file
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint(containerClient.getBlobContainerUrl() + "/" + uniqueFileName + "?" + sasToken)
                .buildClient();

        blobClient.upload(fileInputStream, file.getSize(), true);

        // Set the content type
        BlobHttpHeaders headers = new BlobHttpHeaders();
        headers.setContentType(file.getContentType());
        blobClient.setHttpHeaders(headers);

        String imageUrl = containerClient.getBlobContainerUrl() + "/" + uniqueFileName;

        // Update the user's image URL
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


        return imageUrl;
    }
}
