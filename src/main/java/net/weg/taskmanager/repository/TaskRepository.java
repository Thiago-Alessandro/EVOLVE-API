package net.weg.taskmanager.repository;

<<<<<<< HEAD
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
=======

import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
>>>>>>> feature/security-updated
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> getTaskByCurrentStatus(Status status);
    Collection<Task> getTasksByAssociatesContaining(User user);
    Collection<Task> getTasksByCreatorIs(User user);

    boolean existsByIdAndAssociatesContaining(Long taskId, User user);
    boolean existsByCurrentStatus_IdAndAssociatesContaining(Long statusId, User user);
    Collection<Task> findAllByProject_Id(Long projectId);
}
