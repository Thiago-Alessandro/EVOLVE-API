package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.UserProjectId;
import net.weg.taskmanager.repository.UserProjectRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository repository;

    public UserProject create(UserProject userProject){
        try{
            return repository.save(userProject);
        } catch (Exception e){
            throw new RuntimeException("UserProject with id user: "+ userProject.getUserId() + " project: " + userProject.getProjectId() + " already exists");
        }
    }

    public Collection<UserProject> createAll(Collection<UserProject> userProjects){
        return userProjects != null ? userProjects.stream().map(this::create).toList() : null;
    }

    public UserProject update(UserProject userProject){return repository.save(userProject);}

    public User findProjectCreator(Long projectId){
        Optional<UserProject> optionalUserProject = repository.findUserProjectByProject_IdAndIsManagerIsTrue(projectId);
        if(optionalUserProject.isEmpty()) throw new NoSuchElementException();
        UserProject userProject = optionalUserProject.get();
        return userProject.getUser();
    }

    public Collection<UserProject> findAllWithProjectId(Long projectId){
        Optional<Collection<UserProject>> optionalUserProjects = repository.findUserProjectsByProject_Id(projectId);
        if(optionalUserProjects.isEmpty()) throw new NoSuchElementException();
        return optionalUserProjects.get();
    }

    public boolean existsById(UserProjectId userProjectId){
        return repository.existsById(userProjectId);
    }

    public void delete(UserProject userProject){
        repository.delete(userProject);
    }

}
