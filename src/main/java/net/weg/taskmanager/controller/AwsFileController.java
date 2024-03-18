package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.AwsFile;
import net.weg.taskmanager.service.AwsFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/aws")
@AllArgsConstructor
public class AwsFileController {

    private final AwsFileService awsFileService;

    @PostMapping("/{taskId}")
    public boolean create(@PathVariable Long taskId, @RequestParam MultipartFile file){
        return awsFileService.uploadFile(file, taskId);
    }

}
