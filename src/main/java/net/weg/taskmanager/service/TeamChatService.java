package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamChatConverter;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.TeamChat;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.repository.TeamChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamChatService {

    private final TeamChatRepository teamChatRepository;
    private final UserService userService;

    private final Converter<GetTeamChatDTO, TeamChat> converter = new GetTeamChatConverter();

    public GetTeamChatDTO findById(Long id, Long userId){
        TeamChat teamChat = findTeamChatById(id);
        if(teamChat.getTeam().getParticipants().stream().noneMatch(userTeam -> userTeam.getUserId().equals(userId))) throw new NoSuchElementException("Usuario não pertence à equipe");
        return converter.convertOne(teamChat);
    }

    public TeamChat findTeamChatById(Long teamChatId){
        Optional<TeamChat> teamChat = teamChatRepository.findById(teamChatId);
        if (teamChat.isEmpty()) throw new NoSuchElementException();
        return teamChat.get();
    }

//    public Collection<GetTeamChatDTO> finAll(){
//        Collection<TeamChat> teamChats = teamChatRepository.findAll();
//        return converter.convertAll(teamChats);
//    }

    public Collection<GetTeamChatDTO> findTeamChatsByUserId(Long userId) {
        User user = userService.findUserById(userId);

        Collection<TeamChat> userTeamChats =
                user.getTeamRoles().stream()
                    .map(UserTeam::getUser)
                    .map(team -> teamChatRepository.findTeamChatByTeam_Id(team.getId()))
                    .toList();

        return converter.convertAll(userTeamChats);
    }

}
