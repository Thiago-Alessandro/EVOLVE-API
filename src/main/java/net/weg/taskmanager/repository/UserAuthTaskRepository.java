package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserAuthTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthTaskRepository extends JpaRepository<UserAuthTask, Long> {
}
