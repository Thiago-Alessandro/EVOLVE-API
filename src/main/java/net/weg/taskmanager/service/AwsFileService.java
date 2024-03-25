package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.AwsFile;
import net.weg.taskmanager.repository.AwsFileRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AwsFileService {

    private final AwsFileRepository awsFileRepository;
    private final TaskRepository taskRepository;

    private final Environment env;

    public String findById(String referenceKey){

        String bucketName = env.getProperty("bucket");
        String region = "us-east-1";

        AwsBasicCredentials awsCredentials = getAwsBasicCredentials();

        try (S3Presigner presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(referenceKey)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();
        }
    }


//
//    public AwsFile create(Long referenceId, MultipartFile file){
//        AwsFile awsFile = new AwsFile();
//
//        String keySecret = env.getProperty("keySecret");
//        String bucket = env.getProperty("bucket");
//        String keyId = env.getProperty("keySId");
//
//        AwsCredentials credentials = AwsBasicCredentials.create(keyId,
//                keySecret);
//
//        AmazonS3 s3client = AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(Regions.US_EAST_2)
//                .build();
//
//        awsFile.setAwsKey(UUID.randomUUID());
//        awsFile.setName(file.getOriginalFilename());
//        awsFile.setType(file.getContentType());
//        awsFile.setTask(taskRepository.findById(referenceId).get());
//        return awsFile;
//    }

    public boolean uploadFile(Long referenceId, MultipartFile file) {

        String bucketName = env.getProperty("bucket");
        String region = "us-east-1";

        AwsBasicCredentials awsCredentials = getAwsBasicCredentials();

        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {

            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }

            String fileKey = UUID.randomUUID().toString();
            String contentType = file.getContentType();

            try (InputStream fileInputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .contentType(contentType)
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));

                AwsFile awsFile = new AwsFile();
                awsFile.setAwsKey(fileKey);
                awsFile.setName(file.getOriginalFilename());
                awsFile.setType(contentType);
                awsFile.setTask(taskRepository.findById(referenceId).get());
                awsFileRepository.save(awsFile);
//                return awsFile;

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

    private AwsBasicCredentials getAwsBasicCredentials(){
        String keySecret = env.getProperty("keySecret");
        String keyId = env.getProperty("keyId");
        return AwsBasicCredentials.create(keyId, keySecret);
    }

    private boolean doesBucketExist(S3Client s3Client, String bucketName) {
        try {
            s3Client.headBucket(b -> b.bucket(bucketName));
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }


}
