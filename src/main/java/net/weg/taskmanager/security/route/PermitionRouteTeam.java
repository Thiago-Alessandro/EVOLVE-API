package net.weg.taskmanager.security.route;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
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
    private final UserDetailsEntityRepository repository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetailsEntity.getUser();
        Map<String, String> mapper = object.getVariables();
        Long teamId = Long.parseLong(mapper.get("teamId"));

        return new AuthorizationDecision(validaTeamId(user.getId(), teamId));
    }

    private boolean validaTeamId(Long userId, Long teamId) {
        return repository.existsByUser_Id_AndUser_Teams_Id(userId, teamId);
    }
}
