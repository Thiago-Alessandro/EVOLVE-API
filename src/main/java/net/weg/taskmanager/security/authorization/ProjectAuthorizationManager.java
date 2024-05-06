package net.weg.taskmanager.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.service.ProjectService;
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

@AllArgsConstructor
@Component
public class ProjectAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {


    private final UserService userService;
    private final ProjectPermissionManager permissionManager;

    private final ProjectService projectService;

    @Transactional
    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext reqContext) {
        User user = getUserFromAuthSupplier(supplier);
        String uri = getUriFromAuthRequest(reqContext);
        String method = reqContext.getRequest().getMethod();
        Map<String, String> variables = reqContext.getVariables();
        return isAuthorized(user, method, variables, uri);
    }

    private AuthorizationDecision isAuthorized(User user, String method, Map<String, String> variables, String uri) {
        return switch (method){
            case "POST" -> hasPostPermission(user, variables);
            case "GET" -> hasGetPermission(user, variables);
            case "DELETE" -> hasDeletePermission(user, variables);
            case "PATCH" -> hasPatchPermission(user, variables, uri);
            default -> throw new MethodNotAllowedException(method, getAllowedHttpMethods());
        };
    }

    private AuthorizationDecision hasGetPermission(User user, Map<String, String> variables) {
        if (variables.containsKey("projectId")) return hasGetProjectByIdPermission(user, variables);
        if (variables.containsKey("teamId")) return hasGetProjectByTeamIdPermission(user, variables);
        if (variables.containsKey("userId")) return hasGetProjectByUserIdPermission(user, variables);
        return new AuthorizationDecision(false);
    }

    private AuthorizationDecision hasGetProjectByUserIdPermission(User user,Map<String, String> variables){
        Long userId = Long.parseLong(variables.get("userId"));
        boolean isAuthorized = user.getId().equals(userId);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasGetProjectByTeamIdPermission(User user, Map<String, String> variables){
        Long teamId = Long.parseLong(variables.get("teamId"));
        boolean isAuthorized = user.getTeamRoles().stream().anyMatch(userTeam -> userTeam.getTeamId().equals(teamId));
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasGetProjectByIdPermission(User user, Map<String, String> variables) {
        Long projectId = Long.parseLong(variables.get("projectId"));
        projectService.findProjectById(projectId);
        boolean isAuthorized = permissionManager.hasGetPermission(projectId, user);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasPostPermission(User user, Map<String, String> variables){
        Long teamId = Long.parseLong(variables.get("teamId"));
        return new AuthorizationDecision(permissionManager.hasPostPermission(teamId, user));
    }

    private AuthorizationDecision hasDeletePermission(User user, Map<String, String> variables){
        Long projectId = Long.parseLong(variables.get("projectId"));
        return new AuthorizationDecision(permissionManager.hasDeletePermission(projectId, user));
    }

    private AuthorizationDecision hasPatchPermission(User user, Map<String, String> variables, String uri){
        Long projectId = Long.parseLong(variables.get("projectId"));
        return new AuthorizationDecision(permissionManager.hasPatchPermission(projectId, user, uri));
    }

    private String getUriFromAuthRequest(RequestAuthorizationContext reqContext){
        HttpServletRequest request = reqContext.getRequest();
        return String.valueOf(request.getRequestURI());
    }

    private User getUserFromAuthSupplier(Supplier<Authentication> supplier){
        Authentication auth = supplier.get();
        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();
        User user = userDetails.getUser();
        return userService.findUserById(user.getId());
    }

    private Collection<HttpMethod> getAllowedHttpMethods(){
        return List.of(HttpMethod.POST, HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE);
    }

}
