package net.weg.taskmanager.security.route;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class PermitionRouteProject implements AuthorizationManager<RequestAuthorizationContext> {
    private ProjectRepository repository;
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetails.getUser();
        Map<String, String> mapper = object.getVariables();
        Long projectId = Long.parseLong(mapper.get("projectId"));
        return new AuthorizationDecision(validitionExistsProjectByIdAndUser(projectId, user));
    }

    private boolean validitionExistsProjectByIdAndUser(Long projectId, User user){
        return repository.existsByIdAndMembersContaining(projectId,user);
    }
}
