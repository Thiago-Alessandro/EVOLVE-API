package net.weg.taskmanager.security.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.ProfileAcess;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.repository.ProfileAcessRepository;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserDetailsEntityRepository repository;
    private final UserRepository userRepository;
    private final ProfileAcessRepository profileAcessRepository;

    @PostConstruct
    public void init() {
        createDefaultUser();
        createTeamProfileAccess();
        createProjectProfileAccess();
    }

    private void createTeamProfileAccess() {
        profileAcessRepository.saveAll(List.of(
                new ProfileAcess("TEAM_CREATOR", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_CREATOR, Permission.TEAM_VIEW)),
                new ProfileAcess("TEAM_ADM", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new ProfileAcess("TEAM_COLABORATOR", List.of(Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new ProfileAcess("TEAM_VIEWER", List.of(Permission.TEAM_VIEW))
        ));

    }

    private void createProjectProfileAccess() {
        profileAcessRepository.saveAll(List.of(
                new ProfileAcess("PROJECT_CREATOR", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_CREATOR, Permission.PROJECT_VIEW)),
                new ProfileAcess("PROJECT_ADM", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new ProfileAcess("PROJECT_COLABORATOR", List.of(Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new ProfileAcess("PROJECT_VIEWER", List.of(Permission.PROJECT_VIEW))
        ));
    }

    private void createDefaultUser() {
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
//                    .authorities(List.of(Permission.GET, Permission.POST, Permission.DELETE, Permission.PUT, Permission.PATCH))
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