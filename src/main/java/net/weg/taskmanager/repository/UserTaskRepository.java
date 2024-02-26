package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {

    UserTask findByUserIdAndTaskId(Long userId, Long taskId);

}
