package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.PermissaoDePropriedades;
import net.weg.service.PermissaoDePropriedadesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/permissao/propriedade")
public class PermissaoDePropriedadesController implements IController<PermissaoDePropriedades>{
    private final PermissaoDePropriedadesService permissaoDePropriedadesService;

    @GetMapping("/{id}")
    public ResponseEntity<PermissaoDePropriedades> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(permissaoDePropriedadesService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<PermissaoDePropriedades>> findAll() {
        return new ResponseEntity<>(permissaoDePropriedadesService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            permissaoDePropriedadesService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<PermissaoDePropriedades> create(@RequestBody PermissaoDePropriedades permissaoDePropriedades) {
        return new ResponseEntity<>(permissaoDePropriedadesService.create(permissaoDePropriedades), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PermissaoDePropriedades> update(@RequestBody PermissaoDePropriedades permissaoDePropriedades) {
        return new ResponseEntity<>(permissaoDePropriedadesService.update(permissaoDePropriedades), HttpStatus.OK);
    }
}