package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.model.Projeto;
import net.weg.model.Tarefa;
import net.weg.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    @GetMapping("/{id}")
    public Projeto findById(@PathVariable Integer id) {
        return projetoService.findById(id);
    }

    @GetMapping
    public Collection<Projeto> findAll() {
        return projetoService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        projetoService.delete(id);
    }

    @PostMapping
    public Projeto create(@RequestBody Projeto projeto) {
        return projetoService.create(projeto);
    }

    @PutMapping
    public Projeto update(@RequestBody Projeto projeto) {
        return projetoService.update(projeto);
    }

    //    @GetMapping("/status")
//    public Collection<Status> getStatus(){return projetoService.getAllStatus();}
    @GetMapping("/{id}/tarefa")
    public ResponseEntity<Collection<Tarefa>> getTarefasProjeto(@PathVariable Integer id) {
        Projeto projeto = projetoService.findById(id);
        return new ResponseEntity<>(projetoService.getTarefasProjeto(projeto), HttpStatus.OK);
    }
}
