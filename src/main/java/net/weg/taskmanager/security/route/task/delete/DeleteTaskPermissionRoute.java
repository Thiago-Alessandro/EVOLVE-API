package net.weg.taskmanager.security.route.task.delete;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.route.authorized.UserAuthorizedOnProject;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
public class DeleteTaskPermissionRoute implements AuthorizationManager<RequestAuthorizationContext> {
    private final UserAuthorizedOnProject userAuthorizedOnProject;
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
        Long taskId = Long.parseLong(mapper.get("taskId"));

        if (isUserOnTask(taskId,user)){
            return new AuthorizationDecision(userAuthorizedOnProject.isUserAuthorized(projectId,user, Permission.EDIT_PROJECT_INFO));
        }
            return new AuthorizationDecision(false);
    }

    private boolean isUserOnTask(Long taskId, User user) {
        return taskRepository.existsByIdAndAssociatesContaining(taskId, user);
    }
}
