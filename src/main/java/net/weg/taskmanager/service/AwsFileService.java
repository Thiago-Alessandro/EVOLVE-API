package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.AwsFile;
import net.weg.taskmanager.repository.AwsFileRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
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

import static com.mysql.cj.conf.PropertyKey.logger;

@Service
@AllArgsConstructor
public class AwsFileService {

    private final AwsFileRepository awsFileRepository;
    private final TaskRepository taskRepository;

    public String getAws3(Long id){
        String keyId = env.getProperty("keyId");
        String keySecret = env.getProperty("keySecret");
        AwsBasicCredentials awsBasicCredentials =  AwsBasicCredentials.create(keyId, keySecret);
        String region = "us-east-1";
        String bucketName = env.getProperty("bucket");


        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(Region.of(region))
                .build()) {
            if (doesBucketExist(s3Client, bucketName)) {
                return createPresignedGetUrl(bucketName, awsFileRepository.findById(id).get().getAwsKey(), awsBasicCredentials);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
    public String createPresignedGetUrl(String bucketName, String keyName, AwsBasicCredentials basicCredentials) {
        try (S3Presigner presigner = S3Presigner.builder().region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(basicCredentials)).build()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);


            return presignedRequest.url().toExternalForm();
        }
    }


    private final Environment env;

    public boolean uploadFile(MultipartFile file, long referenceId) {

        String keySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucket");
        String keyId = env.getProperty("keyId");
        String region = "us-east-1";

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(keyId, keySecret);

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


}