package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Collection<Project> findProjectsByMembersContaining(User user);

}
