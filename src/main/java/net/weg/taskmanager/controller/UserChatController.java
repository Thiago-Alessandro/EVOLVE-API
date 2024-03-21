package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserChatDTO;
import net.weg.taskmanager.model.entity.UserChat;
import net.weg.taskmanager.service.UserChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/userChat")
@AllArgsConstructor
public class UserChatController
//        implements IController<UserChat>
{

    private final UserChatService userChatService;

//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GetUserChatDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userChatService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @Override
    @GetMapping()
    public ResponseEntity<Collection<GetUserChatDTO>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

//    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<GetUserChatDTO> delete(@PathVariable Long id) {
        try {
            userChatService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @Override
    @PostMapping
    public ResponseEntity<GetUserChatDTO> create(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.OK);
    }

//    @Override
    @PutMapping()
    public ResponseEntity<GetUserChatDTO> update(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<GetUserChatDTO>> getUserChatsByUserID(@PathVariable Long id) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(id), HttpStatus.OK);
    }
}
