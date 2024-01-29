package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.TaskProjectProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProjectPropertyRepository extends JpaRepository<TaskProjectProperty, Integer> {
}
