package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.model.ProjectChat;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectChatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProjectChatService {

    private final ProjectChatRepository repository;

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

    public ProjectChat findProjectChatByProjectId(Long projectId){
        return repository.findProjectChatByProject_Id(projectId);
    }

}
