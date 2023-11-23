package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRepository extends JpaRepository<Select, Integer> {
}
