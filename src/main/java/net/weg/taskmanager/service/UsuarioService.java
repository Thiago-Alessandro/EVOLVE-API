package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.repository.TarefaRepository;
import net.weg.taskmanager.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;

    public String addBase64Prefix(String base64Image) {
        return "data:image/png;base64," + base64Image;
    }

    public Usuario findById(Integer id){
        Usuario usuario = usuarioRepository.findById(id).get();
        //usuario.getTesteImagem() retorna a representação Base64 da imagem
        usuario.setTesteImagem(addBase64Prefix(usuario.getTesteImagem()));
        return usuario;}

    public Collection<Usuario> findAll(){return usuarioRepository.findAll();}

    public void delete(Integer id){usuarioRepository.deleteById(id);}

    public Usuario create(Usuario usuario){return usuarioRepository.save(usuario);}
    public Usuario update(String usuarioJson, MultipartFile fotoPerfil){
        try {
            Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);
            System.out.println(usuario);
            if(fotoPerfil != null && !fotoPerfil.isEmpty()){
                try {
//                    usuario.setTesteImagem(fotoPerfil.getBytes());
                    System.out.println(usuario.getTesteImagem());
                    System.out.println("1111111111111111111111");
                    usuario.setTesteImagem(Base64.getEncoder().encodeToString(fotoPerfil.getBytes() ) );
                    System.out.println(usuario.getTesteImagem());
                    System.out.println("22222222222222222222222");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(usuario);
            return usuarioRepository.save(usuario);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        if(usuario.getId()!=null){
//           return usuarioRepository.save(usuario);
//        }
//        throw new RuntimeException("Id do usuario não pode ser nulo ao atualizar");
    }

    public Usuario findByEmail(String email){
       return usuarioRepository.findByEmail(email);
    }

}
