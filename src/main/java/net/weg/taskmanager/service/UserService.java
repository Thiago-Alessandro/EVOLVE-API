package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.processor.FileProcessor;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public User findById(Integer id){
        User user = userRepository.findById(id).get();
        //usuario.getTesteImagem() retorna a representação Base64 da imagem
//        if(user.getProfilePicture()!=null){
//            user.setProfilePicture(FileProcessor.addBase64Prefix(user.getProfilePicture()));
//        }
        ResolveStackOverflow.getObjectWithoutStackOverflow(user);
        return user;
    }

    public Collection<User> findAll(){
        Collection<User> users = userRepository.findAll();
        for(User user : users){
            ResolveStackOverflow.getObjectWithoutStackOverflow(user);
        }
        return users;}

    public void delete(Integer id){
        userRepository.deleteById(id);}

    public User create(User user){return userRepository.save(user);}
    public User update(String jsonUser, MultipartFile profilePhoto){
        try {
            User user = objectMapper.readValue(jsonUser, User.class);

            //para não stackar a imagem
            if(user.getProfilePicture().length() > 0){
                user.setProfilePicture(findById(user.getId()).getProfilePicture());
            }



            System.out.println(user);
            if(profilePhoto != null && !profilePhoto.isEmpty()) {
                try {
//                    usuario.setTesteImagem(fotoPerfil.getBytes());
                    user.setProfilePicture(Base64.getEncoder().encodeToString(profilePhoto.getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //excecao aq
            }
            return userRepository.save(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email){
       return userRepository.findByEmail(email);
    }

}
