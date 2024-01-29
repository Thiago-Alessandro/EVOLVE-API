package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissionOfCards;
import net.weg.taskmanager.service.PermissionOfCardsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/permissao/cards")
@AllArgsConstructor
public class PermissionOfCardsController {

    private final PermissionOfCardsService permissionOfCardsService;

    @GetMapping("/{id}")
    public PermissionOfCards findById(@PathVariable Integer id){return permissionOfCardsService.findById(id);}
    @GetMapping
    public Collection<PermissionOfCards> findAll(){return permissionOfCardsService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){permissionOfCardsService.delete(id);}
    @PostMapping
    public PermissionOfCards create(@RequestBody PermissionOfCards permissionOfCards){return permissionOfCardsService.create(permissionOfCards);}
    @PutMapping
    public PermissionOfCards update(@RequestBody PermissionOfCards permissionOfCards){return permissionOfCardsService.update(permissionOfCards);}

}
