package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.repository.ProjectChatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.weg.taskmanager.model.dto.get.GetProjectChatDTO;
import net.weg.taskmanager.model.entity.ProjectChat;
import net.weg.taskmanager.model.entity.User;

@Service
@AllArgsConstructor
public class ProjectChatService {

    private final ProjectChatRepository repository;
    private final UserService userService;

    public ProjectChat create(ProjectChat chat){
        return repository.save(chat);
    }

    public ProjectChat patchUsers(Long chatId, Collection<User> users){
        ProjectChat projectChat = findProjectChatById(chatId);
        projectChat.setUsers(users);
        return repository.save(projectChat);
    }

    public ProjectChat findProjectChatById(Long chatId){
        Optional<ProjectChat> optionalProjectChat = repository.findById(chatId);
        if(optionalProjectChat.isEmpty()) throw new NoSuchElementException();
        return optionalProjectChat.get();
    }

    public ProjectChat findProjectChatByProjectId(Long projectId) {
        return repository.findProjectChatByProject_Id(projectId);
    }

    public Collection<GetProjectChatDTO> findProjectChatsByUserId(Long userId){
        User user = userService.findUserById(userId);

        Collection<ProjectChat> userProjectChats =
                user.getTeamRoles().stream()
                        .map(UserTeam::getTeam)
                        .flatMap(team -> team.getProjects().stream()
                                        .map( project -> repository.findProjectChatByProject_Id(project.getId()))
                                        .filter(projectChat -> projectChat.getUsers().contains(user)))
                        .toList();

        return resolveAndGetDTOS(userProjectChats);
    }

//    public Collection<GetProjectChatDTO> findAll(){
//        Collection<ProjectChat> projects = projectChatRepository.findAll();
//        return resolveAndGetDTOS(projects);
//    }

    private GetProjectChatDTO resolveAndGetDTO(ProjectChat projectChat){
//        chatProcessor.resolveChat(projectChat);
        return new GetProjectChatDTO(projectChat);
    }
    private Collection<GetProjectChatDTO> resolveAndGetDTOS(Collection<ProjectChat> projectChats){
        return projectChats.stream().map(this::resolveAndGetDTO).toList();
    }

}
