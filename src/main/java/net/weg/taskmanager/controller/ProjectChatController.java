package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetProjectChatDTO;
import net.weg.taskmanager.model.entity.ProjectChat;
import net.weg.taskmanager.service.ProjectChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/projectChat")
public class ProjectChatController {

    private final ProjectChatService projectChatService;

//    @GetMapping("/{id}")
//    public TeamChat findById(@PathVariable Long id){return teamChatService.findById(id);}
//
//    @GetMapping
//    public Collection<GetProjectChatDTO> findAll(){
//        return projectChatService.findAll();
//    }

    @GetMapping("/user/{userID}")
    public Collection<GetProjectChatDTO> findProjectChatsByUserId(@PathVariable Long userID){
        return projectChatService.findProjectChatsByUserId(userID);
    }

}
