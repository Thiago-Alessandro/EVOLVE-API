package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.UserProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final UserProcessor userProcessor = new UserProcessor();

    public GetUserDTO findById(Long id){
        User user = userRepository.findById(id).get();
        return resolveAndGetDTO(user);
    }

    public Collection<GetUserDTO> findAll(){
        Collection<User> users = userRepository.findAll();
        return resolveAndGetDTOS(users);
    }

    public void delete(Long id){
        userRepository.deleteById(id);}

    public GetUserDTO create(PostUserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User createdUser = userRepository.save(user);
        setDefaultTeam(createdUser);
        return resolveAndGetDTO(createdUser);
    }

    public GetUserDTO patchImage(Long id, MultipartFile image){
        User user = userRepository.findById(id).get();
        user.setImage(image);
        User updatedUser = userRepository.save(user);
        return resolveAndGetDTO(updatedUser);
    }

    public GetUserDTO update(User updatingUser){
        User user = userRepository.findById(updatingUser.getId()).get();
        modelMapper.map(updatingUser, user);
        User updatedUser  = userRepository.save(user);
        return resolveAndGetDTO(updatedUser);
    }
    
    
    public GetUserDTO update(String jsonUser, MultipartFile image){

        try {
            User user = objectMapper.readValue(jsonUser, User.class);
            user.setImage(image);
            User updatedUser = userRepository.save(user);

            return resolveAndGetDTO(updatedUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public GetUserDTO findByEmail(String email){
        User loggedUser = userRepository.findByEmail(email);
        if(loggedUser != null){
            return resolveAndGetDTO(loggedUser);
        }
        return null;
    }

    public GetUserDTO patchTheme(Long userId,String theme){
        User user = userRepository.findById(userId).get();
        user.setTheme(theme);
        return resolveAndGetDTO(userRepository.save(user));
    }

    public GetUserDTO patchEmail(Long userId,String email){
        User user = userRepository.findById(userId).get();
        user.setEmail(email);
        return resolveAndGetDTO(userRepository.save(user));
    }

    public GetUserDTO patchPassword(Long userId,String password){
        User user = userRepository.findById(userId).get();
        user.setPassword(password);
        return resolveAndGetDTO(userRepository.save(user));
    }


    private GetUserDTO resolveAndGetDTO(User user){
        User resolvedUser = userProcessor.resolveUser(user);
        return new GetUserDTO(resolvedUser);
    }

    private Collection<GetUserDTO> resolveAndGetDTOS(Collection<User> users){
        return users.stream().map(this::resolveAndGetDTO).toList();
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
