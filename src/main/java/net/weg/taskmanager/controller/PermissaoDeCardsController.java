package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.PermissaoDeCards;
import net.weg.taskmanager.service.EquipeService;
import net.weg.taskmanager.service.PermissaoDeCardsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/permissao/cards")
@AllArgsConstructor
public class PermissaoDeCardsController {

    private final PermissaoDeCardsService permissaoDeCardsService;

    @GetMapping("/{id}")
    public PermissaoDeCards findById(@PathVariable Integer id){return permissaoDeCardsService.findById(id);}
    @GetMapping
    public Collection<PermissaoDeCards> findAll(){return permissaoDeCardsService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){permissaoDeCardsService.delete(id);}
    @PostMapping
    public PermissaoDeCards create(@RequestBody PermissaoDeCards permissaoDeCards){return permissaoDeCardsService.create(permissaoDeCards);}
    @PutMapping
    public PermissaoDeCards update(@RequestBody PermissaoDeCards permissaoDeCards){return permissaoDeCardsService.update(permissaoDeCards);}

}
