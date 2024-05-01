//package net.weg.taskmanager.security.route;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import net.weg.taskmanager.model.Project;
//import net.weg.taskmanager.model.Team;
//import net.weg.taskmanager.model.User;
//import net.weg.taskmanager.model.UserTeam;
//import net.weg.taskmanager.repository.*;
//import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
//import net.weg.taskmanager.security.model.enums.Permission;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.function.Supplier;
//
//@Component
//@AllArgsConstructor
//public class PermitionRouteProject implements AuthorizationManager<RequestAuthorizationContext> {
//
//    private ProjectRepository projectRepository;
//    private UserRepository userRepository;
//    private final TeamRepository teamRepository;
//    private final UserTeamRepository userTeamRepository;
//
//    @Override
//    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        AuthorizationManager.super.verify(authentication, object);
//    }
//
//    @Transactional
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
//        Authentication authentication = supplier.get();
//        HttpServletRequest request = object.getRequest();
//
//        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
//        User user = userRepository.findByUserDetailsEntity_Username(userDetails.getUsername()).get();
//
//        Map<String, String> mapper = object.getVariables();
//        Long projectId = Long.parseLong(mapper.get("projectId"));
//        Project project = projectRepository.findById(projectId).get();
//        Team team = teamRepository.findTeamByProjectsContaining(project);
//        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(user.getId(),team.getId());
//        System.out.println("to aaqui");
//        System.out.println(userTeam.getRole().getPermissions());
//        if (isUserOnProject(projectId, user)) {
//            System.out.println(request.getMethod());
//            System.out.println("request.getMethod()");
//            return new AuthorizationDecision(isUserAuthorized(team.getId(), user, Permission.valueOf(request.getMethod())));
//            //deprecated
//        }
//        return new AuthorizationDecision(false);
//    }
//
//    private boolean isUserAuthorized(Long teamId, User user, Permission permission) {
//        return user.getTeamRoles()
//                .stream().filter(teamAcess -> teamAcess.getTeamId().equals(teamId))
//                .anyMatch(teamAcess -> teamAcess.getRole().getPermissions().contains(permission)
//                );
//    }
//
//    private boolean isUserOnProject(Long projectId, User user) {
////        return projectRepository.existsByIdAndMembersContaining(projectId, user);
//        return true;
//    }
//}
