package net.weg.taskmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.model.entity.UserTeamId;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    private final ModelMapper modelMapper;
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private final Converter<GetUserDTO, User> converter = new GetUserConverter();


    public User findUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new NoSuchElementException();
        return optionalUser.get();
    }

    public GetUserDTO findById(Long id){
        User user = findUserById(id);
        return converter.convertOne(user);
    }

    public Collection<GetUserDTO> findAll(){
        Collection<User> users = userRepository.findAll();
        return converter.convertAll(users);
    }

    public void delete(Long id){
        userRepository.deleteById(id);}

    public GetUserDTO create(PostUserDTO userDTO){
//        System.out.println("vou criar");
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
//        System.out.println("estou criando");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setUserDetailsEntity(UserDetailsEntity
                .builder()
                .user(user)
                .enabled(true)
//                    .authorities(List.of(Permission.GET, Permission.POST, Permission.DELETE, Permission.PUT, Permission.PATCH))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username(user.getEmail())
                .password(user.getPassword())
                .build());

        User createdUser = userRepository.save(user);
//        System.out.println("criei1");
        setDefaultTeam(createdUser);
//        System.out.println("criei2");
        return converter.convertOne(createdUser);
    }

    public GetUserDTO findByEmail(String email){
        User loggedUser = userRepository.findByEmail(email);
        if(loggedUser != null){
            return converter.convertOne(loggedUser);
        }
        return null;
    }
    private final RoleService roleService;
    private void setDefaultTeam(User user) {
        Team team = teamService.createTeam(new PostTeamDTO(user));
        team.setName(user.getName() + "'s Team");
        team.setPersonalWorkspace(true);
        team.setImage(user.getImage());
        Team savedTeam = teamService.save(team);
        Role role = roleService.getRoleByName("TEAM_CREATOR");
        UserTeam userTeam = new UserTeam(user.getId(), savedTeam.getId(), user, savedTeam, role, true);
        UserTeam createdUserTeam = userTeamService.create(userTeam);
        user.setTeamRoles(List.of(createdUserTeam));
    }


    public GetUserDTO patchTheme(Long userId,String theme){
        User user = findUserById(userId);
        user.setTheme(theme);
        return converter.convertOne(userRepository.save(user));
    }

    public GetUserDTO patchEmail(Long userId,String email){
        User user = findUserById(userId);
        user.setEmail(email);
        user.getUserDetailsEntity().setUsername(user.getEmail());
        return converter.convertOne(userRepository.save(user));
    }

    public GetUserDTO patchImage(Long id, MultipartFile image){
        User user = findUserById(id);
        user.setImageFromMultipartFile(image);
        User updatedUser = userRepository.save(user);
        return converter.convertOne(updatedUser);
    }

    public GetUserDTO patchName(Long userId,String name){
        User user = findUserById(userId);
        user.setName(name);
        User updatedUser = userRepository.save(user);
        return converter.convertOne(updatedUser);
    }

    public GetUserDTO patchPassword(Long userId,String password){
        User user = findUserById(userId);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.getUserDetailsEntity().setPassword(user.getPassword());
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchPrimaryColor(Long userId,String primaryColor){
        User user = findUserById(userId);
        user.setPrimaryColor(primaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchSecondaryColor(Long userId,String secondaryColor){
        User user = findUserById(userId);
        user.setSecondaryColor(secondaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchPrimaryDarkColor(Long userId,String primaryColor){
        User user = findUserById(userId);
        user.setPrimaryDarkColor(primaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchSecondaryDarkColor(Long userId,String secondaryColor){
        User user = findUserById(userId);
        user.setSecondaryDarkColor(secondaryColor);
        return converter.convertOne(userRepository.save(user));
    }
    public GetUserDTO patchFontSize(Long userId,Integer fontSize){
        User user = userRepository.findById(userId).get();
        user.setFontSize(fontSize);
        return converter.convertOne(userRepository.save(user));
    }


}
