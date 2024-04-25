package net.weg.taskmanager.security.route.project;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.route.authorized.ProjectPermissionManager;
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

    private final ProjectPermissionManager permissionManager;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext reqContext) {

        User user = getUserFromAuthSupplier(supplier);
        String url = getUrlFromAuthRequest(reqContext);
        String method = reqContext.getRequest().getMethod();
        Map<String, String> mapper = reqContext.getVariables();

        return isAuthorized(user, method, mapper, url);
    }

    private AuthorizationDecision isAuthorized(User user, String method, Map<String, String> mapper, String url){
        return switch (method){
            case "POST" -> hasPostPermission(user, mapper);
            case "GET" -> hasGetPermission(user, mapper);
            case "DELETE" -> hasDeletePermission(user, mapper);
            case "PATCH" -> hasPatchPermission(user, mapper, url);
            default -> throw new MethodNotAllowedException(method, getAllowedHttpMethods());
        };
    }

    private AuthorizationDecision hasGetPermission(User user, Map<String, String> mapper){
        Long projectId = Long.parseLong(mapper.get("projectId"));
        boolean isAuthorized = permissionManager.hasGetPermission(projectId, user);
        return new AuthorizationDecision(isAuthorized);
    }

    private AuthorizationDecision hasPostPermission(User user, Map<String, String> mapper){
        Long teamId = Long.parseLong(mapper.get("teamId"));
        return new AuthorizationDecision(permissionManager.hasPostPermission(teamId, user));
    }

    private AuthorizationDecision hasDeletePermission(User user, Map<String, String> mapper){
        Long projectId = Long.parseLong(mapper.get("projectId"));
        return new AuthorizationDecision(permissionManager.hasDeletePermission(projectId, user));
    }

    private AuthorizationDecision hasPatchPermission(User user, Map<String, String> mapper, String url){
        Long projectId = Long.parseLong(mapper.get("projectId"));
        return new AuthorizationDecision(true);
    }



    private String getUrlFromAuthRequest(RequestAuthorizationContext reqContext){
        HttpServletRequest request = reqContext.getRequest();
        String url = String.valueOf(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println(url);
        return url;
    }

    private final UserService userService;

    private User getUserFromAuthSupplier(Supplier<Authentication> supplier){
        Authentication auth = supplier.get();
        System.out.println(auth.getPrincipal());
        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();
        User user = userDetails.getUser();
        return userService.findUserById(user.getId());
    }

    private Collection<HttpMethod> getAllowedHttpMethods(){
        return List.of(HttpMethod.POST, HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE);
    }

}
