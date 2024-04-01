package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.AwsFile;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.repository.AwsFileRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
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

        try (S3Presigner presigner = createS3Presigner(awsCredentials, Region.of(region))) {

            GetObjectRequest objectRequest = createGetObjectRequest(bucketName, referenceKey);

            GetObjectPresignRequest presignRequest = createGetObjectPresignedRequest(objectRequest);

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toString();
        }
    }

    public boolean uploadFile(Long referenceId, MultipartFile file) {

        String bucketName = env.getProperty("bucket");
        String region = "us-east-1";

        AwsBasicCredentials awsCredentials = getAwsBasicCredentials();

        try (S3Client s3Client = createS3Client(awsCredentials, Region.of(region))) {

            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }

            try (InputStream fileInputStream = file.getInputStream()) {

                String fileKey = UUID.randomUUID().toString();
                String contentType = file.getContentType();

                PutObjectRequest putObjectRequest = createPutObjectRequest(bucketName, fileKey, contentType);

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));

                Task task = taskRepository.findById(referenceId).get();
                AwsFile awsFile = new AwsFile(file.getOriginalFilename(), task, fileKey,  contentType);
                awsFileRepository.save(awsFile);
//                return awsFile;
                return true;
            } catch (IOException e) {
//                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    //region auxiliarMethods

    private S3Presigner createS3Presigner(AwsBasicCredentials awsBasicCredentials, Region region){
        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .build();
    }

    private GetObjectRequest createGetObjectRequest(String bucketName, String key){
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }

    private GetObjectPresignRequest createGetObjectPresignedRequest(GetObjectRequest getObjectRequest){
        return  GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                .getObjectRequest(getObjectRequest)
                .build();
    }

    private S3Client createS3Client(AwsBasicCredentials awsBasicCredentials, Region region){
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .build();
    }

    private PutObjectRequest createPutObjectRequest(String bucketName, String key, String contentType){
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();
    }

    private AwsBasicCredentials getAwsBasicCredentials(){
        String keySecret = env.getProperty("keySecret");
        String keyId = env.getProperty("keyId");
        return AwsBasicCredentials.create(keyId, keySecret);
    }

    private boolean doesBucketExist(S3Client s3Client, String bucketName) {
        try {
//            s3Client.headBucket(b -> b.bucket(bucketName));
//            return !s3Client.listBuckets().buckets().stream().filter(bucket -> bucket.name().equals(bucketName)).toList().isEmpty();
            s3Client.headBucket(builder -> builder.bucket(bucketName));
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    //endregion


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

}
