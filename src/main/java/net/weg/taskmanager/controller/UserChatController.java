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
public class UserChatController {
    private final UserChatService userChatService;

    @GetMapping("/{userChatId}")
    public ResponseEntity<GetUserChatDTO> findById(@PathVariable Long userChatId) {
        try {
            return new ResponseEntity<>(userChatService.findById(userChatId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping()
    public ResponseEntity<Collection<GetUserChatDTO>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{userChatId}")
    public ResponseEntity<GetUserChatDTO> delete(@PathVariable Long userChatId) {
        try {
            userChatService.delete(userChatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GetUserChatDTO> create(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GetUserChatDTO> update(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<GetUserChatDTO>> getUserChatsByUserID(@PathVariable Long userId) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(userId), HttpStatus.OK);

    }
}
