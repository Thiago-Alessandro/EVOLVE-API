package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository repository;

    UserProject create(UserProject userProject){
        return repository.save(userProject);
    }

}
