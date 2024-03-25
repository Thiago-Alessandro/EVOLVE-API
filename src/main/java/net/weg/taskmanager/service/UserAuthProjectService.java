package net.weg.taskmanager.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.UserAuthProjectRepository;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserAuthProjectService {
    private final UserAuthProjectRepository userAuthProjectRepository;

    public boolean hasAuthorization(Long usuarioId, Long projetoId, HttpServletRequest request) {
        Auth auth = Auth.valueOf(request.getMethod());
        return userAuthProjectRepository.existsByUserIdAndProjectIdAndAuthorizationsContaining(usuarioId, projetoId, auth);
    }
}
