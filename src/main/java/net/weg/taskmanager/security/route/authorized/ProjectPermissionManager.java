package net.weg.taskmanager.security.route.authorized;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Data
@AllArgsConstructor
@Component
public class ProjectPermissionManager {

    private final UserService userService;

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

    @Transactional
    public boolean hasGetPermission(Long projectId, User user){
        //verifica se o usuario é membro da equipe dona do projeto que ele quer acessar
        User user1 = userService.findUserById(user.getId());
        System.out.println(user1.getProjectRoles());
        return user1.getProjectRoles().stream()
                .filter(projectRole -> projectRole.getProjectId().equals(projectId))
                .anyMatch(userProject -> user1.getTeamRoles().stream()
                        .anyMatch(userTeam -> userTeam.getTeam().equals(userProject.getProject().getTeam())
                        )
                );
    }

    @Transactional
    public boolean hasPostPermission(Long teamId, User user){
        User user1 = userService.findUserById(user.getId());
        //somente quem tem permissao de crair projeeto no grupo pode criar projeto (permissao do grupo)
        return user1.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.CREATE_PROJECT));
    }

    public boolean hasDeletePermission(Long projectId, User user){
        //somente o criador pode excluir o projeto
        return user.getProjectRoles().stream()
                .filter(userProject -> userProject.getProjectId().equals(projectId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.PROJECT_CREATOR));
    }


}
