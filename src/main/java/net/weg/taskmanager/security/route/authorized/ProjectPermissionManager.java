package net.weg.taskmanager.security.route.authorized;


import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.stereotype.Component;

@Component
public class ProjectPermissionManager {

    public boolean hasGetPermission(Long projectId, User user){
        //verifica se o usuario é membro da equipe dona do projeto que ele quer acessar
        return user.getProjectRoles().stream()
                .filter(projectRole -> projectRole.getProjectId().equals(projectId))
                .anyMatch(userProject -> user.getTeamRoles().stream()
                        .anyMatch(userTeam -> userTeam.getTeam().equals(userProject.getProject().getTeam())
                        )
                );
    }

    public boolean hasPostPermission(Long teamId, User user){
        //somente quem tem permissao de crair projeeto no grupo pode criar projeto (permissao do grupo)
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.CREATE_PROJECT));
    }

    public boolean hasDeletePermission(Long projectId, User user){
        //somente o criador pode excluir o projeto
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.PROJECT_CREATOR));
    }

    public boolean hasPatchPermission(Long projectId,User user, String uri){
        return switch (uri){
            case "project/{projectId}/members" -> hasPatchMembersPermission(projectId, user);
            case "project/{projectId}/defaultRole" -> hasPatchRolesPermission(projectId, user);
            case "project/{projectId}/tasks", "project/{projectId}/tasks/remove/{taskId}" ->  hasPatchTasksPermission(projectId, user);
            default -> hasDefaultPatchPermission(projectId, user);
        };
    }

    public boolean hasPatchTasksPermission(Long projectId, User user){
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.PROJECT_CREATOR));
        //quem for colaborador tyabme pode, falta fazer essa logica
    }


    public boolean hasPatchMembersPermission(Long projectId, User user){
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.MANAGE_MEMBERS));
    }

    public boolean hasPatchRolesPermission(Long projectId, User user){
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.PROJECT_CREATOR) || userProject.isManager());
    }

    public boolean hasDefaultPatchPermission(Long projectId,User user){
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.EDIT_PROJECT_INFO));
    }

}
