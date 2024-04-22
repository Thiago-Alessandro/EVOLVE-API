package net.weg.taskmanager.security.route.authorized;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

//@Data
@NoArgsConstructor
@Component
public class UserAuthorizedOnProject  {
    public boolean isUserAuthorized(Long projectId, User user, Permission permission) {
        return user.getProjectsAcess()
                .stream().filter(projectAcess -> projectAcess.getProjectId().equals(projectId))
                .anyMatch(projectAcess -> projectAcess.getRole().getPermissions().contains(Permission.EDIT_PROJECT_INFO)
                );
    }
}
