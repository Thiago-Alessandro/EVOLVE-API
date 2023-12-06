package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.Usuario;
import net.weg.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController implements IController<Usuario> {
    //    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> findAll() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario user) {
        return new ResponseEntity<>(usuarioService.update(user), HttpStatus.CREATED);
    }

//    @PutMapping
//    public ResponseEntity<Usuario> update(
//            @RequestParam String jsonUser,
//            @RequestParam MultipartFile profilePhoto) {
//        return new ResponseEntity<>(usuarioService.update(jsonUser, profilePhoto), HttpStatus.OK);
//    }

    @GetMapping("/login/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        return new ResponseEntity<>(usuarioService.findByEmail(email), HttpStatus.OK);
    }
}