package net.weg.taskmanager.repository;

<<<<<<< HEAD
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.User;
=======

import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.Project;

>>>>>>> feature/security-updated
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findTeamByProjectsContaining(Project project);
<<<<<<< HEAD
    boolean existsByIdAndParticipantsContaining(Long teamId, User user);
=======
    Collection<Team> findTeamsByParticipantsContaining(User user);
>>>>>>> feature/security-updated

//    Team findByProjectsContainingAndParticipantsContaining(Project project, User user);
}
