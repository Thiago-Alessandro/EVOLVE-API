package net.weg.taskmanager.security.route.authorized;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.stereotype.Component;

//@Data
@NoArgsConstructor
@Component
public class ProjectPermissionManager {
    public boolean isUserAuthorized(Long projectId, User user, Permission permission) {
        return user.getProjectRoles()
                .stream().filter(projectAcess -> projectAcess.getProjectId().equals(projectId))
                .anyMatch(projectAcess -> projectAcess.getRole().getPermissions().contains(Permission.EDIT_PROJECT_INFO)
                );
    }

    private boolean hasPermission(Long projectId, User user, Permission permission){
        return user.getProjectRoles()
                .stream().filter(projectRole -> projectRole.getProjectId().equals(projectId))
                .anyMatch(projectRole -> projectRole.getRole().getPermissions().contains(permission)
                );
    }

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
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.PROJECT_CREATOR));
    }

//    public boolean hasDeletePermission(Long projectId, User user){
//        return user.getProjectRoles().stream()
//                .filter(userProject -> userProject.getProjectId().equals(projectId))
//                .anyMatch(userProject -> userProject.getRole().getPermissions().contains());
//    }


}
