package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.Projeto;
import net.weg.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/projeto")
public class ProjetoController implements IController<Projeto> {
    private final ProjetoService projetoService;

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(projetoService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Projeto>> findAll() {
        return new ResponseEntity<>(projetoService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            projetoService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Projeto> create(@RequestBody Projeto projeto) {
        System.out.println("Acessou a controller");
        System.out.println(projeto);
        return new ResponseEntity<>(projetoService.create(projeto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Projeto> update(@RequestBody Projeto projeto) {
        return new ResponseEntity<>(projetoService.update(projeto), HttpStatus.OK);
    }
//    @GetMapping("/status")
//    public Collection<Status> getStatus(){return projetoService.getAllStatus();}
}