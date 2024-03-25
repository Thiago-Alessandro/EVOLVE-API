package net.weg.taskmanager.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.UserAuthTaskRepository;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthTaskService {
    private final UserAuthTaskRepository userAuthTaskRepository;

    public boolean hasAuthorization(Long userId, Long taskId, HttpServletRequest request) {
        Auth auth = Auth.valueOf(request.getMethod());
        return userAuthTaskRepository.existsByUserIdAndTaskIdAndAuthorizationsContaining(userId,taskId,auth);
    }
}
