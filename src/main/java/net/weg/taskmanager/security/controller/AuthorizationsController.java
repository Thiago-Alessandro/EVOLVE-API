package net.weg.taskmanager.security.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.enums.Auth;
import net.weg.taskmanager.security.service.AuthorizationService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/authorization")
@AllArgsConstructor
public class AuthorizationsController {
    private final AuthorizationService authorizationService;

    @GetMapping("/{id}")
    public Collection<Auth> getAllAuthorizations(@PathVariable Long id){
        return authorizationService.getAllAuthorizations(id);
    }
}
