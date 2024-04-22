package net.weg.taskmanager.security.config;

import lombok.AllArgsConstructor;
//import net.weg.taskmanager.security.PermitionRoute;
import net.weg.taskmanager.security.route.project.delete.DeleteProjectPermissionRoute;
import net.weg.taskmanager.security.route.project.get.GetProjectPermissionRoute;
import net.weg.taskmanager.security.route.project.post.PostProjectPermissionRoute;
import net.weg.taskmanager.security.filter.AuthFilter;
//import net.weg.taskmanager.security.route.task.post.PermissionRouteTaskPOST;
import net.weg.taskmanager.security.route.task.delete.DeleteTaskPermissionRoute;
import net.weg.taskmanager.security.route.task.get.GetTasByIdPermissionRoute;
import net.weg.taskmanager.security.route.task.get.GetTaskByStatusPermissionRoute;
import net.weg.taskmanager.security.route.task.post.PostTaskPermissionRoute;
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

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository repo;
    private final AuthFilter authFilter;

    //task
    private final PostTaskPermissionRoute postTaskPermissionRoute;
    private final GetTasByIdPermissionRoute getTasByIdPermissionRoute;
    private final GetTaskByStatusPermissionRoute getTaskByStatusPermissionRoute;
    private final DeleteTaskPermissionRoute deleteTaskPermissionRoute;

    //project
    private final PostProjectPermissionRoute postProjectPermissionRoute;
    private final GetProjectPermissionRoute getProjectPermissionRoute;
    private final DeleteProjectPermissionRoute deleteProjectPermissionRoute;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(autorizeRequests -> autorizeRequests

                //LOGIN
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                //LOGOUT
                .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()

                //TASK
                .requestMatchers(HttpMethod.POST, "/task/{projectId}").access(postTaskPermissionRoute)
                .requestMatchers(HttpMethod.GET, "/task/{projectId}{taskId}").access(getTasByIdPermissionRoute)
                .requestMatchers(HttpMethod.GET, "/task/{projectId}/status/{statusId}").access(getTaskByStatusPermissionRoute)
//                .requestMatchers(HttpMethod.GET, "/task/userTask/{userId}/{taskId}").access(permissionTask)
//                .requestMatchers(HttpMethod.GET, "/task/priorities").access(permissionTask)
//                .requestMatchers(HttpMethod.PUT, "/task").access(permissionTask)
//                .requestMatchers(HttpMethod.PUT, "/task/putProperty").access(permissionTask)
//                .requestMatchers(HttpMethod.PATCH, "task/userTask").access(permissionTask)
//                .requestMatchers(HttpMethod.PATCH, "task/property/{taskId}").access(permissionTask)
                .requestMatchers(HttpMethod.DELETE, "task/{projectId}/{taskId}").access(deleteTaskPermissionRoute)
//
                //PROJECT
                .requestMatchers(HttpMethod.POST, "/project/{teamId}").access(postTaskPermissionRoute)
                .requestMatchers(HttpMethod.GET, "/project/{projectId}").access(getProjectPermissionRoute)
                .requestMatchers(HttpMethod.DELETE, "/project/{projectId}").access(deleteProjectPermissionRoute)
//                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}").access(permissionProject)
//
//                //TEAM
//                .requestMatchers(HttpMethod.GET, "/team/{teamId}").access(permissionTeam)
//                .requestMatchers(HttpMethod.DELETE, "/team/{teamId}").access(permissionTeam)
//
//                //USER
//                .requestMatchers(HttpMethod.GET, "/user/login/{email}").access(permissionUser)
//                .requestMatchers(HttpMethod.GET, "/user/{userId}").access(permissionUser)
//                .requestMatchers(HttpMethod.DELETE, "/user/{userId}").access(permissionUser)
//                .requestMatchers(HttpMethod.PATCH, "/user/{userId}").access(permissionUser)

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