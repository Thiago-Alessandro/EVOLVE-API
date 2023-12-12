package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.model.PermissaoDePropriedades;
import net.weg.service.PermissaoDePropriedadesService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/permissao/propriedade")
public class PermissaoDePropriedadesController {

    private final PermissaoDePropriedadesService permissaoDePropriedadesService;

    @GetMapping("/{id}")
    public PermissaoDePropriedades findById(@PathVariable Integer id){return permissaoDePropriedadesService.findById(id);}
    @GetMapping
    public Collection<PermissaoDePropriedades> findAll(){return permissaoDePropriedadesService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){permissaoDePropriedadesService.delete(id);}
    @PostMapping
    public PermissaoDePropriedades create(@RequestBody PermissaoDePropriedades permissaoDePropriedades){return permissaoDePropriedadesService.create(permissaoDePropriedades);}
    @PutMapping
    public PermissaoDePropriedades update(@RequestBody PermissaoDePropriedades permissaoDePropriedades){return permissaoDePropriedadesService.update(permissaoDePropriedades);}

}
