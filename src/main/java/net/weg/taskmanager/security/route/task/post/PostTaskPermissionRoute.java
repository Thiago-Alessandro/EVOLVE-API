package net.weg.taskmanager.security.route.task.post;

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
@Component
@AllArgsConstructor
public class PostTaskPermissionRoute  implements AuthorizationManager<RequestAuthorizationContext> {

    private final ProjectPermissionManager projectPermissionManager;
    private final ProjectRepository projectRepository;
//    private final PR
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
//        HttpServletRequest request = object.getRequest();

        System.out.println(object.getRequest().getMethod());
        //retorno: POST

        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetails.getUser();

        Map<String, String> mapper = object.getVariables();
        Long projectId = Long.parseLong(mapper.get("projectId"));

//        if(projectRepository.existsByIdAndMembersContaining(projectId, user)){
            return new AuthorizationDecision(projectPermissionManager.isUserAuthorized(projectId,user, Permission.CREATE_TASK));
//        }
//        return new AuthorizationDecision(false);
    }

}