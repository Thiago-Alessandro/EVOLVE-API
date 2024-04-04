package net.weg.taskmanager.security.config;

import lombok.AllArgsConstructor;
//import net.weg.taskmanager.security.PermitionRoute;
import net.weg.taskmanager.security.route.PermitionRouteProject;
import net.weg.taskmanager.security.route.PermitionRouteTeam;
import net.weg.taskmanager.security.filtro.AuthFilter;
import net.weg.taskmanager.security.route.PermitionRouteUser;
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
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository repo;
    private final AuthFilter authFilter;

    private final PermitionRouteTeam permissionTeam;
    private final PermitionRouteProject permissionProject;
    private final PermitionRouteUser permissionUser;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(autorizeRequests -> autorizeRequests
                .requestMatchers(HttpMethod.DELETE, "/project/{projectId}").hasAuthority("DELETE")
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                .requestMatchers(HttpMethod.GET, "/authorization/").permitAll()
                .requestMatchers(HttpMethod.GET, "/team/{teamId}").access(permissionTeam)
                .requestMatchers(HttpMethod.DELETE, "/team/{teamId}").access(permissionTeam)
                .requestMatchers(HttpMethod.GET, "/project/{projectId}").access(permissionProject)
                .requestMatchers(HttpMethod.DELETE, "/project/{projectId}").access(permissionProject)
                .requestMatchers(HttpMethod.PATCH, "/project/{projectId}").access(permissionProject)
                .requestMatchers(HttpMethod.GET,"/user/login/{email}").access(permissionUser)
                .requestMatchers(HttpMethod.GET,"/user/{userId}").access(permissionUser)
                .requestMatchers(HttpMethod.DELETE,"/user/{userId}").access(permissionUser)
                .requestMatchers(HttpMethod.PATCH, "/user/{userId}").access(permissionUser)
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