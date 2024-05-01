package net.weg.taskmanager.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.service.AuthService;
import net.weg.taskmanager.security.util.Cookieutil;
import net.weg.taskmanager.security.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
//@NoArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private AuthService userDetailService;
    private final SecurityContextRepository securityContextRepository;
    private final Cookieutil coookieUtil = new Cookieutil();
    private final JwtUtil jwtUtil = new JwtUtil();

    //executara em cada requisicao da API
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!publicRoute(request)) {

            Cookie cookie;

            try {
                cookie = coookieUtil.getCookie(request, "EV");
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }

            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);
            UserDetails user = userDetailService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user
                    , user.getPassword()
                    , user.getAuthorities()
            );
            //cria contexto novo (vazio) (ja passou pela autenticação pq se nao a autenticacao lanca uma excecao)
            //sem o contexto de autenticacao a autenticacao não fica salva, exigiria autenticacao em toda requisição
            //O contexto mantem o usuario ativo, poderia ser substituido por guardar as informacoes dos usuario logados em algum banco de dados
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            //seta como objeto de autenticao o objeto retornado pela autenticacao ja autenticado (que foi setado isAthenticated como true)
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

            //renovacao do JWT e Cokkie
            Cookie cookieRenovado  = coookieUtil.createCookie(user);
            response.addCookie(cookieRenovado);
        }
        filterChain.doFilter(request, response);
    }

    private boolean publicRoute(HttpServletRequest request) {
        return (request.getRequestURI().equals("/auth/login") && request.getMethod().equals("POST"));
    }
}