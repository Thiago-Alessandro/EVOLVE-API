package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.repository.TeamNotificationRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamNotificationService {
    private final TeamNotificationRepository teamNotificationRepository;

//    public Team taskUpdateNotification(Long userUpdatingId, Long taskId, Long teamId) {
//
//    }
//
//    public Team messagesUpdateNotification(Long userId, Long taskId, Long teamId) {
//
//    }
//
//    public Team projectUpdateNotification(Long userId, Long taskId, Long teamId) {
//
//    }
}
