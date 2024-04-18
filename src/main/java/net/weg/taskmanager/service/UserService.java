package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final Converter<GetUserDTO, User> converter = new GetUserConverter();

    public GetUserDTO findById(Long id){
        User user = userRepository.findById(id).get();
        return converter.convertOne(user);
    }

    public Collection<GetUserDTO> findAll(){
        Collection<User> users = userRepository.findAll();
        return converter.convertAll(users);
    }

    public void delete(Long id){
        userRepository.deleteById(id);}

    public GetUserDTO create(PostUserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User createdUser = userRepository.save(user);
        setDefaultTeam(createdUser);
        return converter.convertOne(createdUser);
    }

    public GetUserDTO patchImage(Long id, MultipartFile image){
        User user = userRepository.findById(id).get();
        user.setImageFromMultipartFile(image);
        User updatedUser = userRepository.save(user);
        return converter.convertOne(updatedUser);
    }

    public GetUserDTO update(User updatingUser){
        User user = userRepository.findById(updatingUser.getId()).get();
        modelMapper.map(updatingUser, user);
        User updatedUser  = userRepository.save(user);
        return converter.convertOne(updatedUser);
    }
    
    
    public GetUserDTO update(String jsonUser, MultipartFile image){

        try {
            User user = objectMapper.readValue(jsonUser, User.class);
            user.setImageFromMultipartFile(image);
            User updatedUser = userRepository.save(user);
            return converter.convertOne(updatedUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public GetUserDTO findByEmail(String email){
        User loggedUser = userRepository.findByEmail(email);
        if(loggedUser != null){
            return converter.convertOne(loggedUser);
        }
        return null;
    }

    public GetUserDTO patchTheme(Long userId,String theme){
        User user = userRepository.findById(userId).get();
        user.setTheme(theme);
        return converter.convertOne(userRepository.save(user));
    }

    public GetUserDTO patchEmail(Long userId,String email){
        User user = userRepository.findById(userId).get();
        user.setEmail(email);
        return converter.convertOne(userRepository.save(user));
    }

    public GetUserDTO patchPassword(Long userId,String password){
        User user = userRepository.findById(userId).get();
        user.setPassword(password);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchPrimaryColor(Long userId,String primaryColor){
        User user = userRepository.findById(userId).get();
        user.setPrimaryColor(primaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchSecondaryColor(Long userId,String secondaryColor){
        User user = userRepository.findById(userId).get();
        user.setSecondaryColor(secondaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchPrimaryDarkColor(Long userId,String primaryColor){
        User user = userRepository.findById(userId).get();
        user.setPrimaryDarkColor(primaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchSecondaryDarkColor(Long userId,String secondaryColor){
        User user = userRepository.findById(userId).get();
        user.setSecondaryDarkColor(secondaryColor);
        return converter.convertOne(userRepository.save(user));
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
