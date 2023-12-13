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
        usuario.setFotoPerfil(addBase64Prefix(usuario.getFotoPerfil()));
        return usuario;}

    public Collection<Usuario> findAll(){return usuarioRepository.findAll();}

    public void delete(Integer id){usuarioRepository.deleteById(id);}

    public Usuario create(Usuario usuario){return usuarioRepository.save(usuario);}
    public Usuario update(String jsonUser, MultipartFile profilePhoto){
        try {
            Usuario user = objectMapper.readValue(jsonUser, Usuario.class);

            //para não stackar a imagem
            if(user.getFotoPerfil().length() > 0){
                user.setFotoPerfil(findById(user.getId()).getFotoPerfil());
            }



            System.out.println(user);
            if(profilePhoto != null && !profilePhoto.isEmpty()) {
                try {
//                    usuario.setTesteImagem(fotoPerfil.getBytes());
                    user.setFotoPerfil(Base64.getEncoder().encodeToString(profilePhoto.getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //excecao aq
            }
            return usuarioRepository.save(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario findByEmail(String email){
       return usuarioRepository.findByEmail(email);
    }

}
