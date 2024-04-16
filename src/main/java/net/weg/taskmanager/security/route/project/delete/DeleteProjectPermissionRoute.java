package net.weg.taskmanager.security.route.project.delete;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.route.authorized.UserAuthorizedOnTeam;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class DeleteProjectPermissionRoute implements AuthorizationManager<RequestAuthorizationContext> {
    private final ProjectRepository projectRepository;

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
        Long projectId = Long.parseLong(mapper.get("projectId"));

        Project project = projectRepository.findById(projectId).get();

        if (isUserOnProject(projectId, user)) {
            return new AuthorizationDecision(isUserCreator(project,user));
        }

        return null;
    }

    private boolean isUserOnProject(Long projectId, User user) {
        return projectRepository.existsByIdAndMembersContaining(projectId, user);
    }

    private boolean isUserCreator(Project project, User user) {
        if (user == project.getCreator()) {
            return true;
        }
        return false;
    }
}
