package net.weg.taskmanager.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.dto.UserLogin;
import net.weg.taskmanager.security.util.Cookieutil;
import net.weg.taskmanager.security.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin user, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authentication);
//            securityContextRepository.saveContext(context, request, response);
//            SecurityContextHolder.setContext(context);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = cookieutil.createCookie(userDetails);
            response.addCookie(cookie);

            return ResponseEntity.ok("Autentificação bem-sucedida");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

    @PostMapping("/logout")
    public /*ResponseEntity<String>*/ void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookie = cookieutil.getCookie(request,"JWT");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
//            return ResponseEntity.ok("Logout bem-sucedido");
        } catch (Exception e) {
            response.setStatus(401);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no logout");
        }
    }
}
