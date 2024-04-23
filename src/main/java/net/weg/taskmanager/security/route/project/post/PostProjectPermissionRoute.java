package net.weg.taskmanager.security.route.project.post;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.route.authorized.UserAuthorizedOnTeam;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
public class PostProjectPermissionRoute implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserAuthorizedOnTeam userAuthorizedOnTeam;
    private final TeamRepository teamRepository;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();

        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetails.getUser();

        Map<String, String> mapper = object.getVariables();

        Long teamId = Long.parseLong(mapper.get("teamId"));

        if (isUserInTeam(teamId, user)) {
            return new AuthorizationDecision(userAuthorizedOnTeam.isUserAuthorized(teamId, user, Permission.CREATE_PROJECT));
        }

        return new AuthorizationDecision(false);
    }

    private boolean isUserInTeam(Long teamId, User user) {
        return teamRepository.existsByIdAndParticipantsContaining(teamId, user);
    }
}
