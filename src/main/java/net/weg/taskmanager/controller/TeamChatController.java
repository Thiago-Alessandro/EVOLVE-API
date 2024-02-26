package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.service.TeamChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/teamChat")
@RestController
@AllArgsConstructor
public class TeamChatController {

    private final TeamChatService teamChatService;

    @GetMapping("/{id}")
    public TeamChat findById(@PathVariable Integer id){
        return teamChatService.findById(id);
    }

    @GetMapping
    public Collection<TeamChat> findAll(){
        return teamChatService.finAll();
    }

    @GetMapping("/user/{id}")
    public Collection<TeamChat> findTeamChatsByUserId(@PathVariable Integer id){
        return teamChatService.findTeamChatsByUserId(id);
    }

}
