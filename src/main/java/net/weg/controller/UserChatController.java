package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.UserChat;
import net.weg.service.UserChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/userChat")
@AllArgsConstructor
public class UserChatController implements IController<UserChat> {
    private final UserChatService userChatService;

    @Override
    public ResponseEntity<UserChat> findById(Integer id) {
        try {
            return new ResponseEntity<>(userChatService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<UserChat>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Integer id) {
//        try {
//            userChatService.delete(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        try {
            userChatService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserChat> create(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserChat> update(UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<UserChat>> getChatsByUserID(@PathVariable Integer id) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(id), HttpStatus.OK);
    }
}