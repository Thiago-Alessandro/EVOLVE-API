package net.weg.taskmanager.repository;

<<<<<<< HEAD
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.security.model.entity.Role;
=======

import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Project;
>>>>>>> feature/security-updated
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

<<<<<<< HEAD
//    boolean existsByIdAndMembersContaining(Long projectId, UserProject user);

//    Project findByProfileAcessesContaining(ProfileAcess profileAcess);
//    boolean existsByIdAndMembersContainingAndTasksContaining(Long projectId, User user, Task task);

//    boolean existsByIdAndRolesContaining(Long id, Role role);
    //taerrado o de cima
//    boolean existsByIdAndMembersContaining(Long projectId, User user);
=======
    Collection<Project> findProjectsByMembersContaining(User user);

>>>>>>> feature/security-updated
}
