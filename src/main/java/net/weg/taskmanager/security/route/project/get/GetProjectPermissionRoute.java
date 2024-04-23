package net.weg.taskmanager.security.route.project.get;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.route.authorized.ProjectPermissionManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
public class GetProjectPermissionRoute implements AuthorizationManager<RequestAuthorizationContext> {
    private final ProjectPermissionManager projectPermissionManager;
    private final ProjectRepository projectRepository;

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

        if (isUserOnProject(projectId, user)) {
            return new AuthorizationDecision(projectPermissionManager.isUserAuthorized(projectId, user, Permission.PROJECT_VIEW));
        }

        return new AuthorizationDecision(false);
    }

    private boolean isUserOnProject(Long projectId, User user) {
        return projectRepository.existsByIdAndMembersContaining(projectId, user);
    }
}
