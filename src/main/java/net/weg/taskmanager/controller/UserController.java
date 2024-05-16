package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.dto.get.GetUserDTO;
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

    private final UserService userService;

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId){
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public GetUserDTO findById(@PathVariable Long userId){
//        System.out.println(userService.findById(userId).getTeamRoles().stream().findFirst().get().getTeam());
        return userService.findById(userId);
}

    @GetMapping
    public Collection<GetUserDTO> findAll(){return userService.findAll();}

    @PostMapping
    public ResponseEntity<GetUserDTO> create(@RequestBody PostUserDTO user){
        try {
            return ResponseEntity.ok(userService.create(user));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<GetUserDTO> loginByEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok(userService.findByEmail(email));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{userId}/name")
    public GetUserDTO patchName(@PathVariable Long userId, @RequestParam String name){
        return userService.patchName(userId, name);
    }


    @PatchMapping("/{userId}")
    public GetUserDTO patchImage(@PathVariable Long userId, @RequestParam MultipartFile image) {
        return userService.patchImage(userId, image);
    }

    @PatchMapping("/{userId}/theme")
    public GetUserDTO patchTheme(@PathVariable Long userId, @RequestParam String theme){
        return userService.patchTheme(userId, theme);
    }

    @PatchMapping("/{userId}/email")
    public GetUserDTO patchEmail(@PathVariable Long userId, @RequestParam String email){
        System.out.println("chego aqui na controler");
        return userService.patchEmail(userId, email);
    }

    @PatchMapping("/{userId}/password")
    public GetUserDTO patchPassword(@PathVariable Long userId, @RequestParam String password){
        return userService.patchPassword(userId, password);
    }
    @PatchMapping("/{userId}/primaryColor")
    public GetUserDTO patchPrimaryColor(@PathVariable Long userId, @RequestParam String primaryColor){
        return userService.patchPrimaryColor(userId, primaryColor);
    }
    @PatchMapping("/{userId}/secondaryColor")
    public GetUserDTO patchSecondaryColor(@PathVariable Long userId, @RequestParam String secondaryColor){
        return userService.patchSecondaryColor(userId, secondaryColor);
    }
    @PatchMapping("/{userId}/primaryDarkColor")
    public GetUserDTO patchPrimaryDarkColor(@PathVariable Long userId, @RequestParam String primaryColor){
        return userService.patchPrimaryDarkColor(userId, primaryColor);
    }
    @PatchMapping("/{userId}/secondaryDarkColor")
    public GetUserDTO patchSecondaryDarkColor(@PathVariable Long userId, @RequestParam String secondaryColor){
        return userService.patchSecondaryDarkColor(userId, secondaryColor);
    }
    @PatchMapping("/{userId}/fontSize")
    public GetUserDTO patchFontSize(@PathVariable Long userId, @RequestParam Integer fontSize){
        return userService.patchFontSize(userId, fontSize);
    }

}
