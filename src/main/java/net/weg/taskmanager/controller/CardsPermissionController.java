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

    @GetMapping("/{cardId}")
    public CardsPermission findById(@PathVariable Long cardId){return permissionOfCardsService.findById(cardId);}
    @GetMapping
    public Collection<CardsPermission> findAll(){return permissionOfCardsService.findAll();}
    @DeleteMapping("/{cardId}")
    public void delete(@PathVariable Long cardId){permissionOfCardsService.delete(cardId);}
    @PostMapping
    public CardsPermission create(@RequestBody CardsPermission cardsPermission){return permissionOfCardsService.create(cardsPermission);}
    @PutMapping
    public CardsPermission update(@RequestBody CardsPermission cardsPermission){return permissionOfCardsService.update(cardsPermission);}

}
