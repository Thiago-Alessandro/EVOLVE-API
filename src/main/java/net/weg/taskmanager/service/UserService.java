package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTeam;
import net.weg.taskmanager.model.UserTeamId;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.UserProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    public User findById(Long id){
        User user = findUserById(id);
        UserProcessor.getInstance().resolveUser(user);
        return user;
    }

    public User findUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new NoSuchElementException();
        return optionalUser.get();
    }

    public Collection<User> findAll(){
        Collection<User> users = userRepository.findAll();
        for(User user : users){
            UserProcessor.getInstance().resolveUser(user);
        }
        return users;}

    public void delete(Long id){
        userRepository.deleteById(id);}

    public User create(PostUserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User createdUser = userRepository.save(user);
        setDefaultTeam(createdUser);
        UserProcessor.getInstance().resolveUser(createdUser);
        return createdUser;}

    public User patchImage(Long id, MultipartFile image){
        User user = findUserById(id);
        user.setImage(image);
        User updatedUser = userRepository.save(user);
        UserProcessor.getInstance().resolveUser(updatedUser);
        return updatedUser;
    }

    public User update(User updatingUser){
        User user = findUserById(updatingUser.getId());
        modelMapper.map(updatingUser, user);
        User updatedUser  = userRepository.save(user);
        return UserProcessor.getInstance().resolveUser(updatedUser);
    }
    
    
    public User update(String jsonUser, MultipartFile image){

        try {
            User user = objectMapper.readValue(jsonUser, User.class);
            user.setImage(image);

            User updatedUser = userRepository.save(user);
            UserProcessor.getInstance().resolveUser(updatedUser);
            return updatedUser;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public User findByEmail(String email){
       return UserProcessor.getInstance().resolveUser(userRepository.findByEmail(email));
    }
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private void setDefaultTeam(User user){
        Team team = teamService.create(new PostTeamDTO(user));
        team.setName(user.getName() + "'s Team");
        team.setPersonalWorkspace(true);
        team.setImage(user.getImage());
        Team savedTeam = teamService.save(team);
        UserTeam userTeam = userTeamService.findById(new UserTeamId(savedTeam.getId(), user.getId()));
        user.setTeamRoles(List.of(userTeam));
    }

}
