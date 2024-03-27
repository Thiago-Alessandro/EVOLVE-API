package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetProjectChatDTO;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.ProjectChat;
import net.weg.taskmanager.model.entity.TeamChat;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.repository.ProjectChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.ChatProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ProjectChatService {

    private final ProjectChatRepository projectChatRepository;
    private final UserRepository userRepository;
    private final ChatProcessor chatProcessor = new ChatProcessor();

    public Collection<GetProjectChatDTO> findProjectChatsByUserId(Long userId){
        User user = userRepository.findById(userId).get();

        Collection<ProjectChat> userProjectChats =
                user.getTeams().stream()
                        .flatMap(team -> team.getProjects().stream()
                                        .map( project -> projectChatRepository.findProjectChatByProject_Id(project.getId())))
                        .toList();


        Collection<GetProjectChatDTO> projectChatDTOS = resolveAndGetDTOS(userProjectChats);
        System.out.println(projectChatDTOS);
        return projectChatDTOS;
    }

    private GetProjectChatDTO resolveAndGetDTO(ProjectChat projectChat){
        chatProcessor.resolveChat(projectChat);
        return new GetProjectChatDTO(projectChat);
    }
    private Collection<GetProjectChatDTO> resolveAndGetDTOS(Collection<ProjectChat> projectChats){
        return projectChats.stream().map(this::resolveAndGetDTO).toList();
    }

}
