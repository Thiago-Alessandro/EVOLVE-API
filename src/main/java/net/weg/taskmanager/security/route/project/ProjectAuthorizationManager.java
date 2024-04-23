package net.weg.taskmanager.security.route.project;

import com.fasterxml.jackson.core.PrettyPrinter;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.route.authorized.ProjectPermissionManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
public class ProjectAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final ProjectPermissionManager permissionManager;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext reqContext) {

        Authentication auth = authentication.get();
        System.out.println(auth.getDetails());

        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();
        User user = userDetails.getUser();

        return isAuthorized(user, reqContext);
    }

    private AuthorizationDecision isAuthorized(User user, RequestAuthorizationContext reqContext){
        String method = reqContext.getRequest().getMethod();
        return switch (method){
            case "POST" -> hasPostPermission(user, reqContext);
            case "GET" -> hasGetPermission(user, reqContext);
            case "DELETE" -> hasDeletePermission(user, reqContext);
            default -> throw new MethodNotAllowedException(method, getAllowedHttpMethods());
        };
    }

    private AuthorizationDecision hasGetPermission(User user, RequestAuthorizationContext reqContext){
        Map<String, String> mapper = reqContext.getVariables();
        Long projectId = Long.parseLong(mapper.get("projectId"));
        boolean isAuthorized = permissionManager.hasGetPermission(projectId, user);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasPostPermission(User user, RequestAuthorizationContext reqContext){
        Map<String, String> mapper = reqContext.getVariables();
        Long teamId = Long.parseLong(mapper.get("teamId"));
        return new AuthorizationDecision(permissionManager.hasPostPermission(teamId, user));
    }

    private AuthorizationDecision hasDeletePermission(User user, RequestAuthorizationContext reqContext){
        Map<String, String> mapper = reqContext.getVariables();
        Long projectId = Long.parseLong(mapper.get("projectId"));
        return new AuthorizationDecision(permissionManager.hasDeletePermission(projectId, user));
    }

    private Collection<HttpMethod> getAllowedHttpMethods(){
        return List.of(HttpMethod.POST, HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE);
    }

}
