package net.weg.taskmanager.security.route;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
//import net.weg.taskmanager.repository.UserHierarchyTeamRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class PermitionRouteTeam implements AuthorizationManager<RequestAuthorizationContext> {
    private final TeamRepository teamRepository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        HttpServletRequest request = object.getRequest();
        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetailsEntity.getUser();
        Map<String, String> mapper = object.getVariables();
        Long teamId = Long.parseLong(mapper.get("teamId"));
        if (isUserInTeam(teamId, user)) {
            return new AuthorizationDecision(isUserAuthorized(teamId, user, Permission.valueOf(request.getMethod())));
        }
        return new AuthorizationDecision(false);
    }

    private boolean isUserInTeam(Long teamId, User user) {
        return teamRepository.existsByIdAndParticipantsContaining(teamId, user);
    }

    private boolean isUserAuthorized(Long teamId, User user, Permission auth) {
        return user.getTeamRoles()
                .stream().filter(teamAcess -> teamAcess.getTeamId().equals(teamId))
                .anyMatch(teamAcess -> teamAcess.getRole().getPermissions().contains(auth)
                );
    }
}
