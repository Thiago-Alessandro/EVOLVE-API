package net.weg.taskmanager.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.dto.UserLogin;
import net.weg.taskmanager.security.util.Cookieutil;
import net.weg.taskmanager.security.util.JwtUtil;
import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final Cookieutil cookieutil = new Cookieutil();
    private final JwtUtil jwtUtil = new JwtUtil();
    private final SecurityContextRepository repository;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(user);
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            System.out.println(authenticationToken);

            System.out.println("1");
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("2");

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            repository.saveContext(context, request, response);
            SecurityContextHolder.setContext(context);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("3");
            Cookie cookie = cookieutil.createCookie(userDetails);
            System.out.println("4");
            response.addCookie(cookie);
            return ResponseEntity.ok("Autentificação bem-sucedida");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage() + "e.getMessage()");
            System.out.println("cai no catch");
            e.printStackTrace();
            throw e;
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookie = cookieutil.getCookie(request, "EV");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
//            return ResponseEntity.ok("Logout bem-sucedido");
        } catch (Exception e) {
            response.setStatus(401);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no logout");
        }
    }
}
