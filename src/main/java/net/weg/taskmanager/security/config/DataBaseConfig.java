package net.weg.taskmanager.security.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Auth;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserDetailsEntityRepository repository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        try {
            repository.findByUsername("teste").get();
        } catch (Exception e) {
            User user = new User();
            user.setName("teste");
            user.setEmail("teste@" + (userRepository.findAll().size() + 1));
            user.setPassword(new BCryptPasswordEncoder().encode("teste123"));
            user.setUserDetailsEntity(UserDetailsEntity
                    .builder()
                    .user(user)
                    .enabled(true)
                    .authorities(List.of(Auth.GET, Auth.POST, Auth.DELETE, Auth.PUT, Auth.PATCH))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .username("teste")
                    .password(new BCryptPasswordEncoder().encode("teste123"))
                    .build());
            userRepository.save(user);
        }
    }
//
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//      UserDetails user = User.withDefaultPasswordEncoder()
//              .username("mi72")
//              .password("M!7dois")
//              .build();
////        UserDetails user2 = User.withDefaultPasswordEncoder()
////                .username("mi72")
////                .password("M!7dois")
////                .build();
//        //
////        UserDetails user = User.builder()
////                .username("mi72")
////                .password("M!7dois")
////                .passwordEncoder(new )
////                .build();
//      return new InMemoryUserDetailsManager();
//    }
}