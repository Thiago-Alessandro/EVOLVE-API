package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.CardsPermission;
import net.weg.taskmanager.service.CardsPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/permission/cards")
@AllArgsConstructor
public class CardsPermissionController {

    private final CardsPermissionService permissionOfCardsService;

    @GetMapping("/{id}")
    public CardsPermission findById(@PathVariable Integer id){return permissionOfCardsService.findById(id);}
    @GetMapping
    public Collection<CardsPermission> findAll(){return permissionOfCardsService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){permissionOfCardsService.delete(id);}
    @PostMapping
    public CardsPermission create(@RequestBody CardsPermission cardsPermission){return permissionOfCardsService.create(cardsPermission);}
    @PutMapping
    public CardsPermission update(@RequestBody CardsPermission cardsPermission){return permissionOfCardsService.update(cardsPermission);}

}
