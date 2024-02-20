package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PriorityRepository extends JpaRepository<Priority,Integer> {

}
