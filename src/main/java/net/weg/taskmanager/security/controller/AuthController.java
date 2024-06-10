package net.weg.taskmanager.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.Producer;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.security.model.dto.UserLogin;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.util.Cookieutil;
import net.weg.taskmanager.security.util.JwtUtil;
import net.weg.taskmanager.service.UserService;
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

import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final Cookieutil cookieutil = new Cookieutil();
    private final JwtUtil jwtUtil = new JwtUtil();
    private final SecurityContextRepository repository;
    private final UserService userService;

    private final Producer topicProducer;


    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody UserLogin user, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            repository.saveContext(context, request, response);
            SecurityContextHolder.setContext(context);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = cookieutil.createCookie(userDetails);
            response.addCookie(cookie);

            GetUserDTO loggedUser = userService.findById(((UserDetailsEntity) authentication.getPrincipal()).getUser().getId());
            return ResponseEntity.ok(loggedUser);
//            return ResponseEntity.ok("authentication");
        } catch (AuthenticationException e) {
            topicProducer.sendErrorMessage("Falha ao tentar realizar login", e.getMessage(), null);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
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
            topicProducer.sendErrorMessage("Falha ao realizar logout", e.getMessage(), null);
            response.setStatus(403);
        }
    }
}
