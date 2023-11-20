package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.service.EquipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/equipe")
@AllArgsConstructor
public class EquipeController {

    private final EquipeService equipeService;

    @GetMapping("/{id}")
    public Equipe findById(@PathVariable Integer id){return equipeService.findById(id);}
    @GetMapping
    public Collection<Equipe> findAll(){return equipeService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){equipeService.delete(id);}
    @PostMapping
    public Equipe create(@RequestBody Equipe equipe){return equipeService.create(equipe);}
    @PutMapping
    public Equipe update(@RequestBody Equipe equipe){return equipeService.update(equipe);}

}
