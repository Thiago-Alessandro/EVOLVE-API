package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.File;
import net.weg.taskmanager.repository.AWSFileRepository;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AWSFileService {
    private final AWSFileRepository aws;
    private final Environment env;

    public boolean create(MultipartFile file){
        String keyID  = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");
        String region = "us-east-1";
        String randomId = UUID.randomUUID().toString();
        File file1 = new File(file, randomId);

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(keyID, keySecret);

        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {

            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }

            try (InputStream fileInputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(randomId)
                        .contentType(file1.getType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));
                aws.save(file1);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean doesBucketExist(S3Client s3Client, String bucketName) {
        try {
            s3Client.headBucket(b -> b.bucket(bucketName));
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    public String getFileUrl(String fileId) {
        String keyID  = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(keyID, keySecret);
        if (doesBucketExist(S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).region(Region.US_EAST_1).build(), bucketName)){
            try (S3Presigner presigner = S3Presigner.builder().region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build()) {

                GetObjectRequest objectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileId)
                        .build();

                GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                        .getObjectRequest(objectRequest)
                        .build();

                PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);



                return presignedRequest.url().toExternalForm();
            } catch (Exception ignore){

            }
        }
return null;

    }

    public String createPresignedUrl(String keyName) {

        String keyID  = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");

        try (S3Presigner presigner = S3Presigner.create()) {

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();


            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();
            System.out.println(myURL);
            return presignedRequest.url().toExternalForm();
        }
    }
}
