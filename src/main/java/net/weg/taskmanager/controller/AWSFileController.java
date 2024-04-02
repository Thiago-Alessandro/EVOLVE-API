package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.AWSFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/aws")
public class AWSFileController {

    private final AWSFileService aws;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestParam MultipartFile file){
        try {
            aws.create(id,file);
            System.out.println("sou da tarefa :" + id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.getStackTrace();
//            System.out.println("não foi nego");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id){
        return aws.get(id);
    }
}
