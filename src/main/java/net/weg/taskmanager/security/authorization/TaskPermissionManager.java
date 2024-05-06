package net.weg.taskmanager.security.authorization;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.service.TaskService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskPermissionManager {

    private final TaskService taskService;

    public boolean hasGetByProjectIdPermission(User user,Long projectId){
        return user.getProjectRoles().stream()
                .anyMatch(userProject -> userProject.getProjectId().equals(projectId));
    }

    public boolean hasEditPermission(User user, Long taskId){
        //somente aqueles que são colaboradores da task ou criadores do projeto
        Task task = taskService.findTaskById(taskId);
        return task.getAssociates().stream()
                .anyMatch(associate -> associate.getId().equals(user.getId())) ||
                task.getProject().getMembers().stream()
                        .anyMatch(userProject -> userProject.isManager() && userProject.getUserId().equals(user.getId()));

    }

    public boolean hasPostPermission(User user, Long projectId){
        //somente quem tem permissao de crair projeeto no grupo pode criar projeto (permissao do grupo)
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.CREATE_TASK));
    }
}
