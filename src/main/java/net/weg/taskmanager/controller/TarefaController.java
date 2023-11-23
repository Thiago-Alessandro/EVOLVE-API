package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.service.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping("/{id}")
    public Tarefa findById(@PathVariable Integer id){return tarefaService.findById(id);}
    @GetMapping
    public Collection<Tarefa> findAll(){return tarefaService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){tarefaService.delete(id);}
    @PostMapping
    public Tarefa create(@RequestBody Tarefa tarefa){
        System.out.println("Controller yay");
        return tarefaService.create(tarefa);}
    @PutMapping
    public Tarefa update(@RequestBody Tarefa tarefa){return tarefaService.update(tarefa);}


}
