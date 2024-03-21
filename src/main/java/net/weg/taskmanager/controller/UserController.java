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
    public GetUserDTO findById(@PathVariable Long id){return userService.findById(id);}
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
    public GetUserDTO findByEmail(@PathVariable String email){return userService.findByEmail(email);}

}
