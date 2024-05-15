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

    Optional<Collection<Project>> findAllByTeam_Id(Long teamId);
}
