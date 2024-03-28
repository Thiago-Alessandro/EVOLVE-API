package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByIdAndMembersContaining(Long projectId, User user);
}
