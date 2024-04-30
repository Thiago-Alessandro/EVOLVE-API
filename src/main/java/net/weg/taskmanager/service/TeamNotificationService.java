package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamNotification;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.values.PropertyValue;
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
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;

    // Function to verify the users that will be notificated
    public Collection<User> verifyNotificatedUsers(Long taskId) {
        Task taskUpdated = taskRepository.findById(taskId).get();
        return new ArrayList<>(taskUpdated.getAssociates());
    }

    public void newCommentNotification(Long userId, Long taskId){
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou um novo comentário na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );
        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deleteCommentNotification(Long userId, Long taskId){
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou um comentário na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );
        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void putSimplePropertyValueNotification(Long userId, Long taskId, Property property) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" alterou o valor da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void putPropertyOptionNotification(Long userId, Long taskId, Property property) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" criou uma nova opção para a propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deletePropertyOptionNotification(Long userId, Long taskId, Property property) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma opção da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void updateFinalDateTaskNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou a data final da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void patchSubtaskNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou uma sub-tarefa a tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deleteSubtaskNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma sub-tarefa da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void patchPropertyNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou uma nova propriedade a tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void patchAssociateNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" associou uma nova pessoa a tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void updatePropertyCurrentOptionNotification(Long taskId, Long userId,Property property) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" atualizou a opção atual da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deletePropertyNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma propriedade da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void patchFileNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou um arquivo a tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deleteFileNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou um arquivo da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deletePropertyCurrentOptionNotification(Long userId, Long taskId, Property property) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma opção atual da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void updateCurrentStatusNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou o status da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void updateCurrentPriorityNotification(Long userId, Long taskId) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou o nível de prioridade da tarefa " + taskUpdated.getName(),
                LocalDateTime.now()
        );

        this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
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
