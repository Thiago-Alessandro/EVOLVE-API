package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class TeamNotificationService {
    private final TeamNotificationRepository teamNotificationRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final DashboardRepository dashboardRepository;

    // Function to verify the task users that will be notificated
    public Collection<User> verifyTaskNotificatedUsers(Long taskId) {
        Task taskUpdated = taskRepository.findById(taskId).get();
        return new ArrayList<>(taskUpdated.getAssociates());
    }

    // Function to verify the project users that will be notificated

    public Collection<User> verifyProjectNotificatedUsers(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        return new ArrayList<>(project.getMembers().stream().map(UserProject::getUser).toList());
    }

    // Task notifications

    public Collection<User> verifyChatNotificationsUser(Long actionUserId, Collection<User> usersChat) {
        Collection<User> newUsersChat = new ArrayList<>();
        usersChat.forEach(user -> {
            if(user.getId() != actionUserId) {
                newUsersChat.add(user);
            }
        });
        return newUsersChat;
    }

    public void newCommentNotification(Long userId, Long taskId){
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou um novo comentário na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );
                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou um comentário na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );
                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" alterou o valor da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" criou uma nova opção para a propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma opção da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou a data final da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou uma sub-tarefa a tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma sub-tarefa da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou uma nova propriedade a tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" associou uma nova pessoa a tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void removeAssociateNotification(Long userId, Long taskId, User removedAssociate) {
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" desassociou "+removedAssociate.getName()+" da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" atualizou a opção atual da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma propriedade da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" adicionou um arquivo a tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou um arquivo da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" deletou uma opção atual da propriedade "+property.getName()+" na tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou o status da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );

                teamNotification.setTeam(teamOfNotification);
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
                this.verifyTaskNotificatedUsers(taskId),
                false,
                userAction.getName()+" mudou o nível de prioridade da tarefa " + taskUpdated.getName(),
                LocalDateTime.now(),
                "task"
        );
                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);

        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    // Project notifications

    public void createDashboardNotification(Long projectId, Long userActionId, Dashboard newDashboard) {
        Project project = this.projectRepository.findById(projectId).get();
        User userAction = this.userRepository.findById(userActionId).get();
        Team teamOfNotification = project.getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId),
                false,
                userAction.getName()+" criou uma nova dashboard no projeto " + project.getName(),
                LocalDateTime.now(),
                "project"
        );
                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);

        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deleteDashboardNotification(Long dashboardId, Long userActionId) {
        User userAction = this.userRepository.findById(userActionId).get();
        Dashboard dashboardDeleted = this.dashboardRepository.findById(dashboardId).get();
        Team teamOfNotification = dashboardDeleted.getProject().getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(dashboardDeleted.getProject().getId()),
                false,
                userAction.getName()+" deletou a dashboard chamada "+dashboardDeleted.getName()+" no projeto " + dashboardDeleted.getProject().getName(),
                LocalDateTime.now(),
                "project"
        );
                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);

        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void patchNewCommentProjectNotification(Long projectId, Long userActionId) {
        User userAction = userRepository.findById(userActionId).get();
        Project project = projectRepository.findById(projectId).get();
        Team teamOfNotification = project.getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId),
                false,
                userAction.getName()+" adicionou um novo comentário no projeto " + project.getName(),
                LocalDateTime.now(),
                "project"
        );
                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void updateProjectStatusList(Long projectId, Long userActionId, Status newStatus) {
        System.out.println(projectId);
        User userAction = userRepository.findById(userActionId).get();
        Project project = projectRepository.findById(projectId).get();
        Team teamOfNotification = project.getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId),
                false,
                userAction.getName()+" adicionou um novo status chamado "+newStatus.getName()+" no projeto " + project.getName(),
                LocalDateTime.now(),
                "project"
        );
                teamNotification.setTeam(teamOfNotification);
this.teamNotificationRepository.save(teamNotification);
        teamOfNotification.getNotifications().add(teamNotification);
        this.teamRepository.save(teamOfNotification);
    }

    public void deleteUserFromProjectNotification(Long projectId, Long userActionId, Set<Long> usersIdRemove) {
        Project project = projectRepository.findById(projectId).get();
        User userAction = userRepository.findById(userActionId).get();
        usersIdRemove.forEach(userId ->{
            User user = userRepository.findById(userId).get();
            TeamNotification teamNotification = new TeamNotification(
                    userAction,
                    this.verifyProjectNotificatedUsers(projectId),
                    false,
                    userAction.getName()+" removeu "+user.getName()+" do projeto "+project.getName(),
                    LocalDateTime.now(),
                    "project"
            );
            this.teamNotificationRepository.save(teamNotification);
            project.getTeam().getNotifications().add(teamNotification);
            this.teamRepository.save(project.getTeam());
        });
    }

    public void addUserToProject(Long projectId, Long userActionId, Long userAddedId) {
        Project project = projectRepository.findById(projectId).get();
        User userAction = userRepository.findById(userActionId).get();
        User userAdded = userRepository.findById(userAddedId).get();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId),
                false,
                userAction.getName()+" adicionou "+userAdded.getName()+" ao projeto " + project.getName(),
                LocalDateTime.now(),
                "project"
        );
        this.teamNotificationRepository.save(teamNotification);
        project.getTeam().getNotifications().add(teamNotification);
        this.teamRepository.save(project.getTeam());
    }

    public void updateProjectInfoNotification(Long projectId, Long userActionId) {
        Project project = projectRepository.findById(projectId).get();
        User userAction = userRepository.findById(userActionId).get();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId),
                false,
                userAction.getName()+" alterou as informações principais do projeto " + project.getName(),
                LocalDateTime.now(),
                "project"
        );

        this.teamNotificationRepository.save(teamNotification);
        project.getTeam().getNotifications().add(teamNotification);
        this.teamRepository.save(project.getTeam());
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
