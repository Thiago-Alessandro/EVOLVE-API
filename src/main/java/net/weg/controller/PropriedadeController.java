package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.Projeto;
import net.weg.model.property.Property;
import net.weg.service.PropriedadeService;
import org.hibernate.type.descriptor.jdbc.H2FormatJsonJdbcType;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/propriedade")
public class PropriedadeController implements IController<Property> {
    private final PropriedadeService propriedadeService;

    @GetMapping("/{id}")
    public ResponseEntity<Property> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(propriedadeService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Property>> findAll() {
        return new ResponseEntity<>(propriedadeService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            propriedadeService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody Property propriedade) {
        return new ResponseEntity<>(propriedadeService.create(propriedade), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Property> update(@RequestBody Property propriedade) {
        return new ResponseEntity<>(propriedadeService.update(propriedade), HttpStatus.OK);
    }
}