package net.weg.taskmanager.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
public class TeamAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserService userService;
    private final TeamPermissionManager permissionManager;

    @Override
    @Transactional
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext reqContext) {
        User user = getUserFromAuthSupplier(supplier);
        String uri = getUriFromAuthRequest(reqContext);
        String method = reqContext.getRequest().getMethod();
        Map<String, String> variables = reqContext.getVariables();
        return isAuthorized(user, method, variables, uri);
    }

    private AuthorizationDecision isAuthorized(User user, String method, Map<String, String> variables, String uri){
        return switch (method){
            case "GET" -> hasGetPermission(user, variables, uri);
            case "DELETE" -> hasDeletePermission(user, variables);
            case "PATCH" -> hasPatchPermission(user, variables, uri);
            default -> throw new MethodNotAllowedException(method, getAllowedHttpMethods());
        };
    }
    private AuthorizationDecision hasGetPermission(User user, Map<String, String> variables, String uri){
        if(uri.contains("/team/user/")) return hasGetTeamsByUserId(user, variables);
        return hasGetTeamById(user, variables);
    }

    private AuthorizationDecision hasGetTeamById(User user, Map<String, String> variables){
        Long teamId = Long.parseLong(variables.get("teamId"));
        boolean isAuthorized = permissionManager.hasGetPermission(teamId, user);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasGetTeamsByUserId(User user, Map<String, String> variables){
        Long userId = Long.parseLong(variables.get("userId"));
        boolean isAuthorized = userId.equals(user.getId());
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasDeletePermission(User user, Map<String, String> variables){
        Long teamId = Long.parseLong(variables.get("teamId"));
        return new AuthorizationDecision(permissionManager.hasDeletePermission(teamId, user));
    }

    private AuthorizationDecision hasPatchPermission(User user, Map<String, String> variables, String uri){
        Long teamId = Long.parseLong(variables.get("teamId"));
        return new AuthorizationDecision(permissionManager.hasPatchPermission(teamId, user, uri));
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
        return List.of(HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE);
    }

}
