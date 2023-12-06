package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.service.TarefaService;
import net.weg.model.Tarefa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/tarefa")
public class TarefaController implements IController<Tarefa> {
    private final TarefaService tarefaService;

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(tarefaService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Tarefa>> findAll() {
        return new ResponseEntity<>(tarefaService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            tarefaService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Tarefa> create(@RequestBody Tarefa tarefa) {
        return new ResponseEntity<>(tarefaService.create(tarefa), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Tarefa> update(@RequestBody Tarefa tarefa) {
        return new ResponseEntity<>(tarefaService.update(tarefa), HttpStatus.OK);
    }
}