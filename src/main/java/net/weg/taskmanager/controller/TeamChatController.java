package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.TeamChat;
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

<<<<<<< HEAD
    @GetMapping("/{teamChatId}")
    public TeamChat findById(@PathVariable Long teamChatId){
        return teamChatService.findById(teamChatId);
=======
    @GetMapping("/{id}")
    public GetTeamChatDTO findById(@PathVariable Long id){
        return teamChatService.findById(id);
>>>>>>> feature/security-updated
    }

    @GetMapping
    public Collection<GetTeamChatDTO> findAll(){
        return teamChatService.finAll();
    }

<<<<<<< HEAD
    @GetMapping("/user/{userId}")
    public Collection<TeamChat> findTeamChatsByUserId(@PathVariable Long userId){
        return teamChatService.findTeamChatsByUserId(userId);
=======
    @GetMapping("/user/{id}")
    public Collection<GetTeamChatDTO> findTeamChatsByUserId(@PathVariable Long id){
        return teamChatService.findTeamChatsByUserId(id);
>>>>>>> feature/security-updated
    }

}
