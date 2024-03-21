package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<Subtask, Long> {
}
