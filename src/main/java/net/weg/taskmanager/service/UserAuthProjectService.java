package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.repository.UserProjectRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserAuthProjectService {
    private final UserProjectRepository userProjectRepository;

//    public boolean hasAuthorization(Long usuarioId, Long projetoId, HttpServletRequest request) {
//        Auth auth = Auth.valueOf(request.getMethod());
//        return userAuthProjectRepository.existsByUserIdAndProjectIdAndAuthorizationsContaining(usuarioId, projetoId, auth);
//    }
}
