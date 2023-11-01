package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Propriedade;
import net.weg.taskmanager.service.PermissaoDeCardsService;
import net.weg.taskmanager.service.PropriedadeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/propriedade")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    @GetMapping("/{id}")
    public Propriedade findById(@PathVariable Integer id){return propriedadeService.findById(id);}
    @GetMapping
    public Collection<Propriedade> findAll(){return propriedadeService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){propriedadeService.delete(id);}
    @PostMapping
    public Propriedade create(@RequestBody Propriedade propriedade){return propriedadeService.create(propriedade);}
    @PutMapping
    public Propriedade update(@RequestBody Propriedade propriedade){return propriedadeService.update(propriedade);}

}
