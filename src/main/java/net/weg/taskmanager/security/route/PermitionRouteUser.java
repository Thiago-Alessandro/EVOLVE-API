package net.weg.taskmanager.security.route;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class PermitionRouteUser implements AuthorizationManager<RequestAuthorizationContext> {
    private final UserDetailsEntityRepository repository;

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
        String email = mapper.get("email");
        if (email == null) {
            Long userId = Long.valueOf(mapper.get("userId"));
            return new AuthorizationDecision(existsById(userId));
        }
        return new AuthorizationDecision(existsByEmail(user.getId(), email));
    }

    private boolean existsById(Long userId) {
        return repository.existsByUser_Id(userId);
    }

    private boolean existsByEmail(Long userId, String email) {
        return repository.existsByUser_IdAndUser_Email(userId, email);
    }
}