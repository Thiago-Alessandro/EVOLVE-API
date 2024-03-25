package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.File;
import net.weg.taskmanager.model.record.AWSFile;
import net.weg.taskmanager.repository.AWSFileRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.presigner.PresignRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;


import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AWSFileService {
    private final AWSFileRepository aws;
    private final Environment env;
    private final TaskRepository taskRepository;

    public String get(Long id) {
        String keyID = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");
        Optional<AWSFile> awsFile = aws.findById(id);
        if (awsFile.isEmpty()) {
            return "is not found";
        }
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(keyID, keySecret);
        if (doesBucketExist(S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).region(Region.US_EAST_1).build(), bucketName)) {
            try (S3Presigner presigner = S3Presigner.builder().credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).region(Region.US_EAST_1).build()) {
                GetObjectRequest objectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(awsFile.get().getChaveAWS())
                        .build();
                GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                        .getObjectRequest(objectRequest)
                        .build();
                PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
                return presignedRequest.url().toExternalForm();
            } catch (Exception e) {
                return "died in the road!";
            }
        }
        return null;
    }

    public boolean create(Long taskId, MultipartFile file) {
        String keyID = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");
        String region = "us-east-1";
        String randomId = UUID.randomUUID().toString();
//        String randomId = "Felipe";
        File file1 = new File(file);

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(keyID, keySecret);
        System.out.println("antes do primeiro try");
        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {
            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }
//            String uuidKey = UUID.randomUUID().toString();
            System.out.println("antes do segundo");
            try (InputStream fileInputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(randomId)
                        .contentType(file1.getType())
                        .build();
                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));
                AWSFile awsFile = new AWSFile();
                aws.save(awsFile);
                awsFile.setChaveAWS(randomId);
                awsFile.setNome(file.getOriginalFilename());
                awsFile.setType(file.getContentType());
                awsFile.setTask(taskRepository.findById(taskId).get());
                aws.save(awsFile);
//                System.out.println(awsFile);
                return true;
            } catch (IOException e) {
                System.out.println("não foi o segundo try");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println("não foi o primeiro try");
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
}
