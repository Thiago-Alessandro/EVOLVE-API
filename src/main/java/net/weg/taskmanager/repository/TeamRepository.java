package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findTeamByProjectsContaining(Project project);

}
