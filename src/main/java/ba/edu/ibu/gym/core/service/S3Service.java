package ba.edu.ibu.gym.core.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class S3Service {

   /* @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3Service() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("us-west-1") // Change to your AWS region
                .build();
    }

    public String uploadFile(InputStream inputStream, String fileName) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        amazonS3.putObject(bucketName, uniqueFileName, inputStream, null);
        return amazonS3.getUrl(bucketName, uniqueFileName).toString();
    }*/
}