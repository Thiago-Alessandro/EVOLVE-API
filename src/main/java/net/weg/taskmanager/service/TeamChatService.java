package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.repository.TeamChatRepository;
import net.weg.taskmanager.repository.UserChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.ChatProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamChatService {

    private final TeamChatRepository teamChatRepository;
    private final UserRepository userRepository;

    public TeamChat findById(Long id){
        TeamChat teamChat = teamChatRepository.findById(id).get();
        ChatProcessor.getInstance().resolveChat(teamChat);
        return teamChat;
    }

    public Collection<TeamChat> finAll(){
        Collection<TeamChat> teamChats = teamChatRepository.findAll();
        teamChats
                .forEach(teamChat -> ChatProcessor.getInstance().resolveChat(teamChat));
        return teamChats;
    }

    public Collection<TeamChat> findTeamChatsByUserId(Long id){
        User user = userRepository.findById(id).get();

        Collection<TeamChat> userTeamChats =
                user.getTeams().stream()
                .map(team -> teamChatRepository.findTeamChatByTeam_Id(team.getId()))
                .toList();

        userTeamChats.
                forEach(teamChat -> ChatProcessor.getInstance().resolveChat(teamChat));

        return userTeamChats;
    }

}
