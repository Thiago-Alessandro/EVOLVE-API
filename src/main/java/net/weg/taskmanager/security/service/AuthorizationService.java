package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Auth;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private final UserDetailsEntityRepository userDetailsEntityRepository;
    private final UserRepository userRepository;

    //id do usuario
    //ai acha o usuarodetail que ele ta linkado e por ai acha as suas autorizacoes
    public Collection<Auth> getAllAuthorizations(Long userId) {
        User user = userRepository.findById(userId).get();
        UserDetailsEntity userDetails = userDetailsEntityRepository.findByUser_Id(user.getId());
        Collection<Auth> autorizacoes = userDetailsEntityRepository.findAllById(userDetails.getId());
        return autorizacoes;
    }
}