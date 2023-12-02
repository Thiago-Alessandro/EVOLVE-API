package net.weg.taskmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonParser;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.service.TarefaService;
import net.weg.taskmanager.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final ObjectMapper objectMapper;
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
    public Usuario update(
                  @RequestParam String jsonUser,
                          @RequestParam MultipartFile profilePhoto){

        return usuarioService.update(jsonUser, profilePhoto);
    }
    @GetMapping("/login/{email}")
    public Usuario findByEmail(@PathVariable String email){return usuarioService.findByEmail(email);}

}
