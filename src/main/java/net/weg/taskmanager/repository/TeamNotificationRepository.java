package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.TeamNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamNotificationRepository extends JpaRepository<TeamNotification,Long> {

}