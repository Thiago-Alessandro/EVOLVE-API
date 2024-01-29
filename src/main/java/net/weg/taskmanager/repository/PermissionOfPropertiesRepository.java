package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.PermissionOfProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionOfPropertiesRepository extends JpaRepository<PermissionOfProperties, Integer> {
}
