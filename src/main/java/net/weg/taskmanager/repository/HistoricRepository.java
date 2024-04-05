package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.Historic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricRepository extends JpaRepository<Historic,Long> {
}
