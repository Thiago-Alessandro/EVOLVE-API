package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.UserProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
    private final ModelMapper modelMapper;

    public User findById(Long id){
        User user = userRepository.findById(id).get();
        UserProcessor.resolveUser(user);
        return user;
    }

    public Collection<User> findAll(){
        Collection<User> users = userRepository.findAll();
        for(User user : users){
            UserProcessor.resolveUser(user);
        }
        return users;}

    public void delete(Long id){
        userRepository.deleteById(id);}

    public User create(PostUserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User createdUser = userRepository.save(user);
        UserProcessor.resolveUser(createdUser);
        return createdUser;}

    public User patchImage(Long id, MultipartFile image){
        User user = userRepository.findById(id).get();
        user.setImage(image);
        User upddatedUser = userRepository.save(user);
        return UserProcessor.resolveUser(upddatedUser);
    }

    public User update(User updatingUser){
        User user = userRepository.findById(updatingUser.getId()).get();
        modelMapper.map(updatingUser, user);
        User updatedUser  = userRepository.save(user);
        return UserProcessor.resolveUser(updatedUser);
    }
    
    
    public User update(String jsonUser, MultipartFile profilePhoto){
        try {
            User user = objectMapper.readValue(jsonUser, User.class);

            //para não stackar a imagem
            if(user.getProfilePicture()!=null && user.getProfilePicture().length() > 0){
                user.setProfilePicture(findById(user.getId()).getProfilePicture());
            }


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
            User updatedUser = userRepository.save(user);
            UserProcessor.resolveUser(updatedUser);
            return updatedUser;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email){

       return UserProcessor.resolveUser(userRepository.findByEmail(email));
    }

}
