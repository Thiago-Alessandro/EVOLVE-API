package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserAuthProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthProjectRepository extends JpaRepository<UserAuthProject, Long> {
}
