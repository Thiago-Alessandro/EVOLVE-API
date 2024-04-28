package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamNotification;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.TeamNotificationRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class TeamNotificationService {
    private final TeamNotificationRepository teamNotificationRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;

//    public Team taskUpdateNotification(Long userUpdatingId, Long taskId, Long teamId) {
//
//    }

    public Collection<User> verifyNotificatedUsers(Long taskId) {
        Collection<User> notificatedUsers = new ArrayList<>();
        Task taskUpdated = taskRepository.findById(taskId).get();
        notificatedUsers.addAll(taskUpdated.getAssociates());
        return notificatedUsers;
    }

    public void newCommentNotification(Long userId, Long taskId){
        User userAction = userRepository.findById(userId).get();
        GetTaskDTO taskUpdated = taskService.findById(taskId);
        ShortTeamDTO teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou um novo comentário na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );
        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamNotificationRepository.save(teamNotification);
    }
//
//    public Team messagesUpdateNotification(Long userId, Long taskId, Long teamId) {
//
//    }
//
//    public Team projectUpdateNotification(Long userId, Long taskId, Long teamId) {
//
//    }
}
