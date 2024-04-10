package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
//import net.weg.taskmanager.security.repository.AuthRepository;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private UserDetailsEntityRepository repository;
//    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsEntity> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
//        else{
//            userOptional = userRepository.findByUsuarioDetailsEntity_Id()
//        }
        throw new UsernameNotFoundException("Invalidated caches");
    }

//    public Auth create(String name) {
//        try {
//            if (Auth.valueOf(name) != null) {
//                return Auth.valueOf(name);
//            }
//        } catch (NullPointerException e) {
//            throw new RuntimeException("There is no authorization registered with that name in our system!");
//        }
//        return null;
//    }
}