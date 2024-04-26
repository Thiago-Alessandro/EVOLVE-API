package net.weg.taskmanager.security.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.repository.RoleRepository;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserDetailsEntityRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        createDefaultUser();
        createDefaultUser2();
        createTeamProfileAccess();
        createProjectProfileAccess();
    }

    private void createTeamProfileAccess() {
        roleRepository.saveAll(List.of(
                new Role("TEAM_CREATOR", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_CREATOR, Permission.TEAM_VIEW)),
                new Role("TEAM_ADM", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new Role("TEAM_COLABORATOR", List.of(Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new Role("TEAM_VIEWER", List.of(Permission.TEAM_VIEW))
        ));

    }

    private void createProjectProfileAccess() {
        roleRepository.saveAll(List.of(
                new Role("PROJECT_CREATOR", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_CREATOR, Permission.PROJECT_VIEW)),
                new Role("PROJECT_ADM", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new Role("PROJECT_COLABORATOR", List.of(Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new Role("PROJECT_VIEWER", List.of(Permission.PROJECT_VIEW))
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

    private void createDefaultUser2() {
        try {
            repository.findByUsername("teste2").get();
        } catch (Exception e) {
            User user = new User();
            user.setName("teste2");
            user.setEmail("teste2@" + (userRepository.findAll().size() + 1));
            user.setPassword(new BCryptPasswordEncoder().encode("teste123"));
            user.setUserDetailsEntity(UserDetailsEntity
                    .builder()
                    .user(user)
                    .enabled(true)
//                    .authorities(List.of(Permission.GET, Permission.POST, Permission.DELETE, Permission.PUT, Permission.PATCH))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .username("teste2")
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