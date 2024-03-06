package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.values.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyValueRepository extends JpaRepository<PropertyValue,Integer> {
}
