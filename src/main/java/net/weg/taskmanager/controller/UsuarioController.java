package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.service.TarefaService;
import net.weg.taskmanager.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Integer id){return usuarioService.findById(id);}
    @GetMapping
    public Collection<Usuario> findAll(){return usuarioService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){usuarioService.delete(id);}
    @PostMapping
    public Usuario create(@RequestBody Usuario usuario){return usuarioService.create(usuario);}
    @PutMapping
    public Usuario update(@RequestBody Usuario usuario){return usuarioService.update(usuario);}
    @GetMapping("/login/{email}")
    public Usuario findByEmail(@PathVariable String email){return usuarioService.findByEmail(email);}

}
