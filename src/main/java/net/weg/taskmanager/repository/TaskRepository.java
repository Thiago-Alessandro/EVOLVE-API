package net.weg.taskmanager.repository;


import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> getTaskByCurrentStatus(Status status);
    Collection<Task> getTasksByAssociatesContaining(User user);
    Collection<Task> getTasksByCreatorIs(User user);

//    @Override
    Optional<Task> findTaskById(Long taskId);

    boolean existsByIdAndAssociatesContaining(Long taskId, User user);
    boolean existsByCurrentStatus_IdAndAssociatesContaining(Long statusId, User user);
    Collection<Task> findAllByProject_Id(Long projectId);

    Collection<Task> findAllByDependenciesContaining(Task task);
}
