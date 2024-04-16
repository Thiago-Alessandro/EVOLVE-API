package net.weg.taskmanager.security.route.authorized;

import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizedOnTeam {
    public boolean isUserAuthorized(Long teamId, User user, Permission auth) {
        return user.getTeamAcess()
                .stream().filter(teamAcess -> teamAcess.getTeamId().equals(teamId))
                .anyMatch(teamAcess -> teamAcess.getAcessProfile().getAuths().contains(auth)
                );
    }
}
