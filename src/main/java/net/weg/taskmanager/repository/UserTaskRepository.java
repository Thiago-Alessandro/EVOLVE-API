package net.weg.taskmanager.repository;

<<<<<<< HEAD
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
=======
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.model.entity.UserTaskId;
>>>>>>> feature/security-updated
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
    boolean existsByUserIdAndTaskId(Long userId, Long taskId);
    UserTask findByUserIdAndTaskId(Long userId, Long taskId);
}
