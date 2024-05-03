package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    boolean existsByIdAndMembersContaining(Long projectId, UserProject user);

//    Project findByProfileAcessesContaining(ProfileAcess profileAcess);
//    boolean existsByIdAndMembersContainingAndTasksContaining(Long projectId, User user, Task task);

//    boolean existsByIdAndRolesContaining(Long id, Role role);
    //taerrado o de cima
//    boolean existsByIdAndMembersContaining(Long projectId, User user);

    Optional<Collection<Project>> findByTeamId(Long teamId);
    Collection<Project> findProjectsByMembersContaining(UserProject userProject);
}
