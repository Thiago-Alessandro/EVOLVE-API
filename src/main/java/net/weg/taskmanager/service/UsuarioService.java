package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.repository.TarefaRepository;
import net.weg.taskmanager.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario findById(Integer id){return usuarioRepository.findById(id).get();}

    public Collection<Usuario> findAll(){return usuarioRepository.findAll();}

    public void delete(Integer id){usuarioRepository.deleteById(id);}

    public Usuario create(Usuario usuario){return usuarioRepository.save(usuario);}
    public Usuario update(Usuario usuario){
        System.out.println(usuario);
        return usuarioRepository.save(usuario);}

    public Usuario findByEmail(String email){
       return usuarioRepository.findByEmail(email);
    }

}
