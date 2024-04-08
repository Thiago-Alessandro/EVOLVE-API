package net.weg.taskmanager.security.route;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Auth;
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
    private UserRepository userRepository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Transactional
    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        HttpServletRequest request = object.getRequest();
        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
        User user = userRepository.findByUserDetailsEntity_Username(userDetails.getUsername()).get();
        Map<String, String> mapper = object.getVariables();
        Long projectId = Long.parseLong(mapper.get("projectId"));
        if (isUserOnProject(projectId, user)) {
            return new AuthorizationDecision(isUserAuthorized(projectId, user, Auth.valueOf(request.getMethod())));
        }
        return new AuthorizationDecision(false);
    }

    private boolean isUserAuthorized(Long projectId, User user, Auth auth) {
        return user.getProjectsAcess()
                .stream().filter(projectAcess ->  projectAcess.getProjectId().equals(projectId))
                .anyMatch(projectAcess ->projectAcess.getAcessProfile().getAuths().contains(auth)
                );
//        return repository.existsByIdAndMembersContaining(projectId,user);
    }

    private boolean isUserOnProject(Long projectId, User user) {
        return repository.existsByIdAndMembersContaining(projectId, user);
    }
}
