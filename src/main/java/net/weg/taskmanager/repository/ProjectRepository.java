package net.weg.taskmanager.repository;


import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Collection<Project> findProjectsByMembersContaining(User user);

}
