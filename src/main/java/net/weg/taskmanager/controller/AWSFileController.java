package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.AWSFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
