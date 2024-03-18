package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.UserProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    public GetUserDTO findById(Long id){
        User user = userRepository.findById(id).get();
        return UserProcessor.getInstance().resolveUser(user);
    }

    public Collection<GetUserDTO> findAll(){
        Collection<User> users = userRepository.findAll();
        Collection<GetUserDTO> getUserDTOS = new HashSet<>();
        for(User user : users){
            getUserDTOS.add(UserProcessor.getInstance().resolveUser(user));
        }
        return getUserDTOS;
    }

    public void delete(Long id){
        userRepository.deleteById(id);}

    public GetUserDTO create(PostUserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User createdUser = userRepository.save(user);
        setDefaultTeam(createdUser);
        return UserProcessor.getInstance().resolveUser(createdUser);
    }

    public GetUserDTO patchImage(Long id, MultipartFile image){
        User user = userRepository.findById(id).get();
        user.setImage(image);
        User updatedUser = userRepository.save(user);
        return UserProcessor.getInstance().resolveUser(updatedUser);
    }

    public GetUserDTO update(User updatingUser){
        User user = userRepository.findById(updatingUser.getId()).get();
        modelMapper.map(updatingUser, user);
        User updatedUser  = userRepository.save(user);
        return UserProcessor.getInstance().resolveUser(updatedUser);
    }
    
    
    public GetUserDTO update(String jsonUser, MultipartFile image){

        try {
            User user = objectMapper.readValue(jsonUser, User.class);
            user.setImage(image);

            User updatedUser = userRepository.save(user);
            return UserProcessor.getInstance().resolveUser(updatedUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public GetUserDTO findByEmail(String email){
       return UserProcessor.getInstance().resolveUser(userRepository.findByEmail(email));
    }

    private User setDefaultTeam(User user){
        if (user.getTeams()==null){
            user.setTeams(new ArrayList<>());
        }

        Team defaultTeam = new Team(user);

        user.getTeams().add(teamRepository.save(defaultTeam));
        return user;
    }

}
