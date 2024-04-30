package net.weg.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @GetMapping("/{userId}")
    public User findById(@PathVariable Long userId){return userService.findById(userId);}
    @GetMapping
    public Collection<User> findAll(){return userService.findAll();}
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId){
        userService.delete(userId);}
    @PostMapping
    public ResponseEntity<User> create(@RequestBody PostUserDTO user){
        try {
            return ResponseEntity.ok(userService.create(user));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping
    public User update(@RequestBody User user){
        return userService.update(user);
    }
    
    @PutMapping("/full")
    public User update(
                  @RequestParam String jsonUser,
                          @RequestParam MultipartFile profilePhoto){

        return userService.update(jsonUser, profilePhoto);
    }
    
    @PatchMapping("/{userId}")
    public User patchImage(@PathVariable Long userId, @RequestParam MultipartFile image){
        return userService.patchImage(userId, image);
    }
    @GetMapping("/login/{email}")
    public User findByEmail(@PathVariable String email){return userService.findByEmail(email);}

}
