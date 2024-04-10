package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.entity.ProfileAcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByIdAndMembersContaining(Long projectId, User user);

//    Project findByProfileAcessesContaining(ProfileAcess profileAcess);
    boolean existsByIdAndMembersContainingAndTasksContaining(Long projectId, User user, Task task);

    boolean existsByIdAndProfileAcessesContaining(Long id, ProfileAcess profileAcess);
}
