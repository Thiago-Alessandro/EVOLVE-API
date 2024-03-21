package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.TeamChat;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.repository.TeamChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.ChatProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TeamChatService {

    private final TeamChatRepository teamChatRepository;
    private final UserRepository userRepository;
    private final ChatProcessor chatProcessor = new ChatProcessor();

    public GetTeamChatDTO findById(Long id){
        TeamChat teamChat = teamChatRepository.findById(id).get();
        return resolveAndGetDTO(teamChat);
    }

    public Collection<GetTeamChatDTO> finAll(){
        Collection<TeamChat> teamChats = teamChatRepository.findAll();
        return resolveAndGetDTOS(teamChats);
    }

    public Collection<GetTeamChatDTO> findTeamChatsByUserId(Long id){
        User user = userRepository.findById(id).get();

        Collection<TeamChat> userTeamChats =
                user.getTeams().stream()
                .map(team -> teamChatRepository.findTeamChatByTeam_Id(team.getId()))
                .toList();

        return resolveAndGetDTOS(userTeamChats);
    }

    private GetTeamChatDTO resolveAndGetDTO(TeamChat teamChat){
        chatProcessor.resolveChat(teamChat);
        return new GetTeamChatDTO(teamChat);
    }
    private Collection<GetTeamChatDTO> resolveAndGetDTOS(Collection<TeamChat> teamChats){
        return teamChats.stream().map(this::resolveAndGetDTO).toList();
    }

}
