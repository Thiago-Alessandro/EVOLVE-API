package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.model.entity.UserTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {

    UserTask findByUserIdAndTaskId(Long userId, Long taskId);

}
