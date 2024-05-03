package net.weg.taskmanager.security.route.authorized;

import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.stereotype.Component;

@Component
public class TeamPermissionManager {
    public boolean hasGetPermission(Long teamId, User user){
        //O usuario pode ver somente as equipes que faz parte
        return user.getTeamRoles().stream()
                .anyMatch(userTeam -> userTeam.getTeamId().equals(teamId));
    }

    public boolean hasDeletePermission(Long teamId, User user){
        //somente o criador pode excluir a equipe
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userProject -> userProject.getRole().getPermissions().contains(Permission.PROJECT_CREATOR) || userProject.isManager());
    }


    public boolean hasPatchPermission(Long teamId,User user, String uri){
        return switch (uri){
            case "team/{teamId}/participants" -> hasPatchMembersPermission(teamId, user);
            case "team/{teamId}/roles", "team/{teamId}/deafaultRole" -> hasPatchRolesPermission(teamId, user);
            default -> hasDefaultPatchPermission(teamId, user);
        };
    }

    public boolean hasPatchRolesPermission(Long teamId,User user){
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.TEAM_CREATOR) || userTeam.isManager());
    }

    public boolean hasPatchMembersPermission(Long teamId, User user){
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.MANAGE_PARTICIPANTS));
    }

    public boolean hasDefaultPatchPermission(Long teamId,User user){
        return user.getTeamRoles().stream()
                .filter(userTeam -> userTeam.getTeamId().equals(teamId))
                .anyMatch(userTeam -> userTeam.getRole().getPermissions().contains(Permission.EDIT_TEAM_INFO));
    }
}
