package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.service.EquipeService;
import net.weg.model.Equipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/equipe")
@AllArgsConstructor
public class EquipeController implements IController<Equipe> {
    private final EquipeService equipeService;

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(equipeService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Equipe>> findAll() {
        return new ResponseEntity<>(equipeService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            equipeService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Equipe> create(@RequestBody Equipe equipe) {
        return new ResponseEntity<>(equipeService.create(equipe), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Equipe> update(@RequestBody Equipe equipe) {
        return new ResponseEntity<>(equipeService.update(equipe), HttpStatus.OK);
    }
}