package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.AWSFileRepository;
import net.weg.taskmanager.service.AWSFileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("aws")
public class AWSFileController {

    private final AWSFileService aws;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam MultipartFile file){
        try {
            aws.create(file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{bucketID}")
    public ResponseEntity<?> getFileUrl(@PathVariable String bucketID) {
        String url = aws.getFileUrl(bucketID);
        return url != null ? ResponseEntity.ok().body(url) : ResponseEntity.internalServerError().build();
    }


}
