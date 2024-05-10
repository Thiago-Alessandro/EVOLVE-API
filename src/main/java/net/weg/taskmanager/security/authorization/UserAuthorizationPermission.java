package net.weg.taskmanager.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component

public class UserAuthorizationPermission implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext reqContext) {
        User user = getUserFromAuthSupplier(supplier);
        Map<String, String> variables = reqContext.getVariables();
        return new AuthorizationDecision(isAuthorized(user, variables));
    }

    public boolean isAuthorized(User user, Map<String, String> variables){
        Long userId = Long.parseLong(variables.get("userId"));
        return user.getId().equals(userId);
    }

    private User getUserFromAuthSupplier(Supplier<Authentication> supplier){
        Authentication auth = supplier.get();
        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();
        return userDetails.getUser();
    }
}
