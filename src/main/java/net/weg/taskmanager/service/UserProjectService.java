package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.UserProjectId;
import net.weg.taskmanager.model.dto.UserProjectDTO;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.repository.UserProjectRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.repository.RoleRepository;
import net.weg.taskmanager.security.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository repository;

    public UserProject create(UserProject userProject){
        return repository.save(userProject);
    }

    public Collection<UserProject> findAllByUserId(Long userId){
        Optional<Collection<UserProject>> optionalUserProjects = repository.findUserProjectsByUser_Id(userId);
        if(optionalUserProjects.isEmpty()) throw new NoSuchElementException();
        return optionalUserProjects.get();
    }

    public Collection<UserProject> createAll(Collection<UserProject> userProjects){
        return userProjects != null ? userProjects.stream().map(this::create).toList() : null;
    }

    public UserProject update(UserProject userProject){return repository.save(userProject);}

    public User findProjectCreator(Long projectId){
        Optional<UserProject> optionalUserProject = repository.findUserProjectByProject_IdAndManagerIsTrue(projectId);
        if(optionalUserProject.isEmpty()) throw new NoSuchElementException();
        UserProject userProject = optionalUserProject.get();
        return userProject.getUser();
    }

    public Collection<UserProject> findAllWithProjectId(Long projectId){
        Optional<Collection<UserProject>> optionalUserProjects = repository.findUserProjectsByProject_Id(projectId);
        if(optionalUserProjects.isEmpty()) throw new NoSuchElementException();
        return optionalUserProjects.get();
    }
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    public UserProjectDTO setRole(UserProject userProject) {
        Role roleToSet = roleRepository.findByName(userProject.getRole().getName());
        userProject.setRole(roleToSet);
        userProject.setProject(projectRepository.findById(userProject.getProjectId()).get());
        userProject.setUser(userRepository.findById(userProject.getUserId()).get());
        return new UserProjectDTO(repository.save(userProject));
    }

    public boolean existsById(UserProjectId userProjectId){
        return repository.existsById(userProjectId);
    }

    public void delete(UserProject userProject){
        repository.delete(userProject);
    }

}
