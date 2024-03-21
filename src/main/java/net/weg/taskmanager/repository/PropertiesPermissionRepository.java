package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.PropertiesPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesPermissionRepository extends JpaRepository<PropertiesPermission, Long> {
}
