package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.TeamNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamNotificationRepository extends JpaRepository<TeamNotification,Long> {

    Collection<TeamNotification> findAllByTeam_Id(Long teamId);

}
