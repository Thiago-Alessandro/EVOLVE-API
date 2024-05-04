package net.weg.taskmanager.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class TaskAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserService userService;
    private final TaskPermissionManager taskPermissionManager;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext reqContext) {
        User user = getUserFromAuthSupplier(supplier);
        String method = reqContext.getRequest().getMethod();
        Map<String, String> variables = reqContext.getVariables();
        return isAuthorized(user, method, variables);
    }

    private AuthorizationDecision isAuthorized(User user, String method, Map<String, String> variables){
        return switch (method){
            case "GET" -> hasGetPermission(user, variables); //user/projet/       team tem msm?
            case "PATCH", "DELETE" -> hasEditPermission(user, variables);
            default -> throw new MethodNotAllowedException(method, getAllowedHttpMethods());
        };
    }

    private AuthorizationDecision hasGetPermission(User user, Map<String, String> variables){
        if(variables.containsKey("userId")) return hasGetByUserIdPermission(user, variables);
        if(variables.containsKey("projectId")) return hasGetByProjectIdPermission(user, variables);
        return new AuthorizationDecision(false);
    }

    private AuthorizationDecision hasGetByProjectIdPermission(User user, Map<String, String> variables){
        Long projectId = Long.parseLong(variables.get("projectId"));
        boolean isAuthorized = taskPermissionManager.hasGetByProjectIdPermission(user, projectId);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasGetByUserIdPermission(User user, Map<String, String> variables){
        Long userId = Long.parseLong(variables.get("userId"));
        boolean isAuthorized = userId.equals(user.getId());
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasEditPermission(User user, Map<String, String> variables){
        Long taskId = Long.parseLong(variables.get("taskId"));
        boolean isAuthorized = taskPermissionManager.hasEditPermission(user, taskId);
        return new AuthorizationDecision(isAuthorized);
    }

    private User getUserFromAuthSupplier(Supplier<Authentication> supplier){
        Authentication auth = supplier.get();
        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();
        User user = userDetails.getUser();
        return userService.findUserById(user.getId());
    }

    private Collection<HttpMethod> getAllowedHttpMethods(){
        return List.of(HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE);
    }

}
