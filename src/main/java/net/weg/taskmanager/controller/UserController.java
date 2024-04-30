package net.weg.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public GetUserDTO findById(@PathVariable Long id){
        return userService.findById(id);}
    @GetMapping
    public Collection<GetUserDTO> findAll(){return userService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);}
    @PostMapping
    public GetUserDTO create(@RequestBody PostUserDTO user){return userService.create(user);}
    
    @PutMapping
    public GetUserDTO update(@RequestBody User user){
        return userService.update(user);
    }
    
    @PutMapping("/full")
    public GetUserDTO update(
                  @RequestParam String jsonUser,
                          @RequestParam MultipartFile profilePhoto){

        return userService.update(jsonUser, profilePhoto);
    }
    
    @PatchMapping("/{id}")
    public GetUserDTO patchImage(@PathVariable Long id, @RequestParam MultipartFile image){
        return userService.patchImage(id, image);
    }
    @GetMapping("/login/{email}")
    public GetUserDTO loginByEmail(@PathVariable String email){return userService.findByEmail(email);}

    @PatchMapping("/theme/{userId}/{theme}")
    public GetUserDTO patchTheme(@PathVariable Long userId, @PathVariable String theme){
        return userService.patchTheme(userId, theme);
    }

    @PatchMapping("/email/{userId}/{email}")
    public GetUserDTO patchEmail(@PathVariable Long userId, @PathVariable String email){
        return userService.patchEmail(userId, email);
    }

    @PatchMapping("/password/{userId}/{password}")
    public GetUserDTO patchPassword(@PathVariable Long userId, @PathVariable String password){
        return userService.patchPassword(userId, password);
    }
    @PatchMapping("/primaryColor/{userId}")
    public GetUserDTO patchPrimaryColor(@PathVariable Long userId, @RequestParam String primaryColor){
        return userService.patchPrimaryColor(userId, primaryColor);
    }
    @PatchMapping("/secondaryColor/{userId}")
    public GetUserDTO patchSecondaryColor(@PathVariable Long userId, @RequestParam String secondaryColor){
        return userService.patchSecondaryColor(userId, secondaryColor);
    }
    @PatchMapping("/primaryDarkColor/{userId}")
    public GetUserDTO patchPrimaryDarkColor(@PathVariable Long userId, @RequestParam String primaryColor){
        return userService.patchPrimaryDarkColor(userId, primaryColor);
    }
    @PatchMapping("/secondaryDarkColor/{userId}")
    public GetUserDTO patchSecondaryDarkColor(@PathVariable Long userId, @RequestParam String secondaryColor){
        return userService.patchSecondaryDarkColor(userId, secondaryColor);
    }

}
