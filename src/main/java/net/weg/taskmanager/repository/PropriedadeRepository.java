package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository  extends JpaRepository<Property, Integer> {
}
