package net.weg.taskmanager.security.route;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserTaskRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

//import java.util.Map;
import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class PermissionRouteTask implements AuthorizationManager<RequestAuthorizationContext> {
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;

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
        Long statusId = Long.parseLong(mapper.get("statusId"));
        if (statusId == null) {
            Long taskId = Long.parseLong(mapper.get("taskId"));
            Long userId = Long.parseLong(mapper.get("userId"));
            if (userId == null) {
                return new AuthorizationDecision(existsByTaskIdAndUser(taskId, user));
            }
            return new AuthorizationDecision(existsByTask_IdAndUser_Id(userId, taskId, userDetails));
        }
        return new AuthorizationDecision();
    }

    private boolean existsByTaskIdAndUser(Long taskId, User user) {
        return taskRepository.existsByIdAndAssociatesContaining(taskId, user);
    }

    private boolean existsByTask_IdAndUser_Id(Long userId, Long taskId, UserDetailsEntity userDetails) {
        return userTaskRepository.existsByUserIdAndTaskIdAndUser_UserDetailsEntity(userId, taskId, userDetails);
    }
//    private boolean existsByStatus(Long)
}
