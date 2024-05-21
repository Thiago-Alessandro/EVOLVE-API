package net.weg.taskmanager.security.config;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.authorization.*;
import net.weg.taskmanager.security.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository repo;
    private final AuthFilter authFilter;

    private final ProjectAuthorizationManager projectAuthorizationManager;
    private final TeamAuthorizationManager teamAuthorizationManager;
    private final TaskAuthorizationManager taskAuthorizationManager;
    private final UserAuthorizationPermission userAuthorizationPermission;

    private final CorsConfigurationSource corsConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.cors(cors -> cors.configurationSource(corsConfig));

        httpSecurity.authorizeHttpRequests(autorizeRequests -> autorizeRequests

                //AUTH
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/login/{email}").permitAll()

                //TASK
                .requestMatchers(HttpMethod.POST, "/task/project/{projectId}").access(taskAuthorizationManager)
                //tem q fazer a logica p criar ainda (se o usuario tem permissao createTask no projeto ou ismanager)
                .requestMatchers(HttpMethod.GET, "/task/project/{projectId}").access(taskAuthorizationManager)
                .requestMatchers(HttpMethod.GET, "/task/user/{userId}").access(taskAuthorizationManager)
//                .requestMatchers(HttpMethod.GET, "/task/{projectId}/status/{statusId}").access(getTaskByStatusPermissionRoute)
                .requestMatchers(HttpMethod.PATCH, "/task/setConcluded").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/task/{taskId}/**").access(taskAuthorizationManager)
                .requestMatchers(HttpMethod.DELETE, "/task/{taskId}/**").access(taskAuthorizationManager)


//                .requestMatchers(HttpMethod.GET, "/task/userTask/{userId}/{taskId}").access(permissionTask)
//                .requestMatchers(HttpMethod.GET, "/task/priorities").access(permissionTask)
//                .requestMatchers(HttpMethod.PUT, "/task").access(permissionTask)
//                .requestMatchers(HttpMethod.PUT, "/task/putProperty").access(permissionTask)
//                .requestMatchers(HttpMethod.PATCH, "task/userTask").access(permissionTask)
//                .requestMatchers(HttpMethod.PATCH, "task/property/{taskId}").access(permissionTask)

//                .requestMatchers(HttpMethod.DELETE, "task/{projectId}/{taskId}").access(deleteTaskPermissionRoute)
//
                //PROJECT

                .requestMatchers(HttpMethod.GET, "/project/team/{teamId}").access(projectAuthorizationManager)
                .requestMatchers(HttpMethod.GET, "/project/user/{userId}").access(projectAuthorizationManager)
                .requestMatchers(HttpMethod.GET, "/project/{projectId}").access(projectAuthorizationManager)
                .requestMatchers(HttpMethod.GET, "/project/{projectId}/**").access(projectAuthorizationManager)

                .requestMatchers(HttpMethod.POST, "/project/team/{teamId}").access(projectAuthorizationManager)

                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}/member").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/project/{projectId}").access(projectAuthorizationManager)

                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}/**").access(projectAuthorizationManager)
                //o patch pode ser assim para todos que nao possuem outras variaveis alem do projectId na requisicao
//                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}/members").access(projectAuthorizationManager)
//                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}/tasks").access(projectAuthorizationManager)
//                cpa task cria uma por vez


//                //TEAM
                .requestMatchers(HttpMethod.GET, "/team/{teamId}").access(teamAuthorizationManager)
//                .requestMatchers(HttpMethod.GET, "/team/user/{userId}").access(teamAuthorizationManager)
                .requestMatchers(HttpMethod.PUT, "/team/code/{teamId}/participant").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/team/{teamId}/**").access(teamAuthorizationManager)
                .requestMatchers(HttpMethod.DELETE, "/team/{teamId}").access(teamAuthorizationManager)
//                //USER
//                .requestMatchers(HttpMethod.GET, "/user/login/{email}").access(userAuthorizationPermission)
//                .requestMatchers(HttpMethod.GET, "/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.DELETE, "/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.PATCH, "/user/{userId}/image/link").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/user/{userId}/**").access(userAuthorizationPermission)
//                .requestMatchers(HttpMethod.PATCH, "/user/{userId}/email/{email}").access(userAuthorizationPermission)

                //USERCHAT
                .requestMatchers(HttpMethod.GET, "/userChat/{userChatId}/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.GET, "/userChat/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.PUT, "/userChat/{userChatId}/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.PATCH, "/userChat/{userChatId}/user/{userId}").access(userAuthorizationPermission)
                .requestMatchers(HttpMethod.DELETE, "/userChat/{userChatId}/user/{userId}").access(userAuthorizationPermission)

                //TEAMCHAT
                .requestMatchers(HttpMethod.GET, "/teamChat/user/{userId}").access(userAuthorizationPermission)

                //PROJECTCHAT
                .requestMatchers(HttpMethod.GET, "/project/user/{userId}").access(userAuthorizationPermission)

                .anyRequest().authenticated());

        httpSecurity.securityContext((context) -> context.securityContextRepository(repo));

//        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.logout(Customizer.withDefaults());
        httpSecurity.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        httpSecurity.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(autenticacaoService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    //determina a forma de busca do usuario a ser autenticada
    //gera um autentification manager padrao
//    @Autowired
//    public void config(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService((autenticacaoService)).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }
}