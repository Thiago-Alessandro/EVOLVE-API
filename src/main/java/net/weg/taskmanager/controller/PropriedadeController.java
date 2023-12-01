package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.service.PropriedadeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/propriedade")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    @GetMapping("/{id}")
    public Property findById(@PathVariable Integer id){return propriedadeService.findById(id);}
    @GetMapping
    public Collection<Property> findAll(){return propriedadeService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){propriedadeService.delete(id);}
    @PostMapping
    public Property create(@RequestBody Property propriedade){return propriedadeService.create(propriedade);}
    @PutMapping
    public Property update(@RequestBody Property propriedade){return propriedadeService.update(propriedade);}

}
