package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.property.Property;
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

    private enum NotificatioType{
        TASK_PROPERTIES,
        TASK_DESCRIPTION,
        TASK_NAME,
        TASK_ASSOCIATES,
        TASK_SUBTASKS,
        TASK_COMMENTS,
        TASK_ATTACHMENTS,
        TASK_FINAL_DATE,
        TASK_STATUS,
        TASK_PRIORITY,
        PROJECT_DASHBOARDS,
        PROJECT_COMMENTS,
        PROJECT_STATUS,
        PROJECT_PARTICIPANTS,
        PROJECT_INFOS
    }

    private Collection<User> filterNotificatedUserByConfig(Collection<User> users, NotificatioType notificatioType){
        return switch (notificatioType){
            case TASK_PROPERTIES -> users.stream().filter(user -> user.getNotificationsConfig().isTaskProperties()).toList();
            case TASK_DESCRIPTION -> users.stream().filter(user -> user.getNotificationsConfig().isTaskDescription()).toList();
            case TASK_NAME -> users.stream().filter(user -> user.getNotificationsConfig().isTaskName()).toList();
            case TASK_ASSOCIATES -> users.stream().filter(user -> user.getNotificationsConfig().isTaskAssociates()).toList();
            case TASK_SUBTASKS -> users.stream().filter(user -> user.getNotificationsConfig().isTaskSubtasks()).toList();
            case TASK_COMMENTS -> users.stream().filter(user -> user.getNotificationsConfig().isTaskComments()).toList();
            case TASK_ATTACHMENTS -> users.stream().filter(user -> user.getNotificationsConfig().isTaskAttachments()).toList();
            case TASK_FINAL_DATE -> users.stream().filter(user -> user.getNotificationsConfig().isTaskFinalDate()).toList();
            case TASK_STATUS -> users.stream().filter(user -> user.getNotificationsConfig().isTaskStatus()).toList();
            case TASK_PRIORITY -> users.stream().filter(user -> user.getNotificationsConfig().isTaskPriority()).toList();
            case PROJECT_DASHBOARDS -> users.stream().filter(user -> user.getNotificationsConfig().isProjectDashboards()).toList();
            case PROJECT_COMMENTS -> users.stream().filter(user -> user.getNotificationsConfig().isProjectComments()).toList();
            case PROJECT_STATUS -> users.stream().filter(user -> user.getNotificationsConfig().isProjectStatus()).toList();
            case PROJECT_PARTICIPANTS -> users.stream().filter(user -> user.getNotificationsConfig().isProjectParticipants()).toList();
            case PROJECT_INFOS -> users.stream().filter(user -> user.getNotificationsConfig().isProjectInfos()).toList();
        };
    }

    // Function to verify the task users that will be notificated
    public Collection<User> verifyTaskNotificatedUsers(Long taskId, NotificatioType notificatioType) {
        Task taskUpdated = taskRepository.findById(taskId).get();
        Collection<User> notificatedUsers = new ArrayList<>(taskUpdated.getAssociates());
        notificatedUsers = notificatedUsers.stream().filter(user -> user.getNotificationsConfig() != null && user.getNotificationsConfig().isTaskAll()).toList();
        return filterNotificatedUserByConfig(notificatedUsers, notificatioType);
    }

    // Function to verify the project users that will be notificated
    public Collection<User> verifyProjectNotificatedUsers(Long projectId, NotificatioType notificatioType) {
        Project project = projectRepository.findById(projectId).get();
        Collection<User> notificatedUsers = new ArrayList<>(project.getMembers().stream().map(UserProject::getUser).toList());
        notificatedUsers = notificatedUsers.stream().filter(user -> user.getNotificationsConfig() != null && user.getNotificationsConfig().isProjectAll()).toList();
        return filterNotificatedUserByConfig(notificatedUsers, notificatioType);
    }

    // Task notifications

    public Collection<User> verifyChatNotificationsUser(Long actionUserId, Collection<User> usersChat) {
        Collection<User> newUsersChat = new ArrayList<>();
        usersChat.forEach(user -> {
            if(!Objects.equals(user.getId(), actionUserId)) {
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_COMMENTS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_COMMENTS),
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

    public void patchTaskDescriptionNotification(Long userId, Long taskId){
        User userAction = userRepository.findById(userId).get();
        Task taskUpdated = taskRepository.findById(taskId).get();
        Team teamOfNotification = taskUpdated.getProject().getTeam();


        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_DESCRIPTION),
                false,
                userAction.getName()+" alterou a descrição da tarefa " + taskUpdated.getName() + " no projeto " + taskUpdated.getProject().getName(),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_FINAL_DATE),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_SUBTASKS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_SUBTASKS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_ASSOCIATES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_ASSOCIATES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_ATTACHMENTS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_ATTACHMENTS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PROPERTIES),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_STATUS),
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
                this.verifyTaskNotificatedUsers(taskId, NotificatioType.TASK_PRIORITY),
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
                this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_DASHBOARDS),
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
                this.verifyProjectNotificatedUsers(dashboardDeleted.getProject().getId(), NotificatioType.PROJECT_DASHBOARDS),
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
                this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_COMMENTS),
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
        User userAction = userRepository.findById(userActionId).get();
        Project project = projectRepository.findById(projectId).get();
        Team teamOfNotification = project.getTeam();

        TeamNotification teamNotification = new TeamNotification(
                userAction,
                this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_STATUS),
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
                    this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_PARTICIPANTS),
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
                this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_PARTICIPANTS),
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
                this.verifyProjectNotificatedUsers(projectId, NotificatioType.PROJECT_INFOS),
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
