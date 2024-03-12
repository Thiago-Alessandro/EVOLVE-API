package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.AWSFileRepository;
import net.weg.taskmanager.service.AWSFileService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
