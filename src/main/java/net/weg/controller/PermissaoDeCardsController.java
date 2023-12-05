package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.PermissaoDeCards;
import net.weg.service.PermissaoDeCardsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/permissao/cards")
@AllArgsConstructor
public class PermissaoDeCardsController implements IController<PermissaoDeCards> {
    private final PermissaoDeCardsService permissaoDeCardsService;

    @GetMapping("/{id}")
    public ResponseEntity<PermissaoDeCards> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(permissaoDeCardsService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<PermissaoDeCards>> findAll() {
        return new ResponseEntity<>(permissaoDeCardsService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            permissaoDeCardsService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<PermissaoDeCards> create(@RequestBody PermissaoDeCards permissaoDeCards) {
        return new ResponseEntity<>(permissaoDeCardsService.create(permissaoDeCards), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PermissaoDeCards> update(@RequestBody PermissaoDeCards permissaoDeCards) {
        return new ResponseEntity<>(permissaoDeCardsService.update(permissaoDeCards), HttpStatus.OK);
    }
}