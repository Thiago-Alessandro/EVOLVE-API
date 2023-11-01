package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.service.ProjetoService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    @GetMapping("/{id}")
    public Projeto findById(@PathVariable Integer id){return projetoService.findById(id);}
    @GetMapping
    public Collection<Projeto> findAll(){return projetoService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){projetoService.delete(id);}
    @PostMapping
    public Projeto create(@RequestBody Projeto projeto){return projetoService.create(projeto);}
    @PutMapping
    public Projeto update(@RequestBody Projeto projeto){return projetoService.update(projeto);}

}
