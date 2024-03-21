package net.weg.taskmanager.repository;


import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> getTaskByCurrentStatus(Status status);
    Collection<Task> getTasksByAssociatesContaining(User user);
    Collection<Task> getTasksByCreatorIs(User user);

}
