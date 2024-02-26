package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.CardsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsPermissionRepository extends JpaRepository<CardsPermission, Long> {
}
