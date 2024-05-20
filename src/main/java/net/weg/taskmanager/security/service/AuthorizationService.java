package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserDetailsEntityRepository userDetailsEntityRepository;

    public Collection<Permission> getAllAuthorizations(Long userId) {
        UserDetailsEntity userDetails = userDetailsEntityRepository.findByUser_Id(userId);
        return userDetails.getAuthorities();
    }
}