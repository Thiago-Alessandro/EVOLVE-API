package net.weg.taskmanager.security.route.task.get;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.TaskRepository;
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

@Component
@AllArgsConstructor
public class GetTaskByStatusPermissionRoute implements AuthorizationManager<RequestAuthorizationContext> {
    private final ProjectPermissionManager projectPermissionManager;
    private final TaskRepository taskRepository;

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
        Long statusId = Long.parseLong(mapper.get("statusId"));

        if (isUserOnTask(statusId, user)) {
            return new AuthorizationDecision(projectPermissionManager.isUserAuthorized(projectId, user, Permission.PROJECT_VIEW));
        }

        return new AuthorizationDecision(false);
    }

    private boolean isUserOnTask(Long statusId, User user) {
        return taskRepository.existsByCurrentStatus_IdAndAssociatesContaining(statusId, user);
    }
}
