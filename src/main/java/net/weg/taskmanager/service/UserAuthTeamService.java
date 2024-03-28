package net.weg.taskmanager.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.UserAuthTeamRepository;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthTeamService {
    private final UserAuthTeamRepository userAuthTeamRepository;

//    public boolean hasAuthorization(Long userId, Long teamId, HttpServletRequest request){
//        Auth auth = Auth.valueOf(request.getMethod());
//        return userAuthTeamRepository.existsByUserIdAndTeamIdAndAuthorizationsContaining(userId,teamId,auth);
//    }
}
