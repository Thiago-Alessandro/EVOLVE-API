package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.service.UserChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/userChat")
@AllArgsConstructor
public class UserChatController implements IController<UserChat> {

    private final UserChatService userChatService;

    @Override
    public ResponseEntity<UserChat> findById(Long id) {
        try {
            return new ResponseEntity<>(userChatService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Collection<UserChat>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserChat> delete(Long id) {
        try {
            userChatService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<UserChat> create(UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserChat> update(UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<UserChat>> getChatsByUserID(@PathVariable Long id) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(id), HttpStatus.OK);
    }
}
