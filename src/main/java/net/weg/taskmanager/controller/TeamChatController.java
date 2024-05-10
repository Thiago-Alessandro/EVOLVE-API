package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
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

    @GetMapping("/{teamChatId}/user/{userId}")
    public GetTeamChatDTO findById(@PathVariable Long teamChatId, @PathVariable Long userId){
        return teamChatService.findById(teamChatId, userId);
    }

//    @GetMapping
//    public Collection<GetTeamChatDTO> findAll(){
//        return teamChatService.finAll();
//    }

    @GetMapping("/user/{userId}")
    public Collection<GetTeamChatDTO> findTeamChatsByUserId(@PathVariable Long userId){
        return teamChatService.findTeamChatsByUserId(userId);
    }

}
