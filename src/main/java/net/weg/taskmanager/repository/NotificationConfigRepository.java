package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.NotificationsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationConfigRepository extends JpaRepository<NotificationsConfig, Long> {
}
