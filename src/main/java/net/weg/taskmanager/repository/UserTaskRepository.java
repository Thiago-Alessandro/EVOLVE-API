package net.weg.taskmanager.repository;


import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.model.entity.UserTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
    boolean existsByUserIdAndTaskId(Long userId, Long taskId);
    Optional<UserTask> findByUserIdAndTaskId(Long userId, Long taskId);
}
