package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamChatConverter;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.ProjectChat;
import net.weg.taskmanager.model.entity.TeamChat;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.repository.TeamChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamChatService {

    private final TeamChatRepository teamChatRepository;
    private final UserRepository userRepository;

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
        User user = userRepository.findById(userId).get();

        Collection<TeamChat> userTeamChats =
                user.getTeamRoles().stream()
                    .map(UserTeam::getTeamId)
                    .map(teamChatRepository::findTeamChatByTeam_Id)
                        .filter(Objects::nonNull) //não é para ser necessario pq todos os teams devem ter chat
                    .toList();

        return converter.convertAll(userTeamChats);
    }

    public TeamChat patchUsers(Long chatId, Collection<User> users){
        TeamChat teamChat = findTeamChatById(chatId);
        teamChat.setUsers(users);
        return teamChatRepository.save(teamChat);
    }

}
