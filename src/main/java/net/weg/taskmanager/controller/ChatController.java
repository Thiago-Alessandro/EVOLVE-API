package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController implements IController<Chat> {

    private final ChatService chatService;

    @Override
    public ResponseEntity<Chat> findById(Integer id) {
        try {
            return new ResponseEntity<>(chatService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Collection<Chat>> findAll() {
        return new ResponseEntity<>(chatService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Chat> delete(Integer id) {
        try {
            chatService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Chat> create(Chat obj) {
        return new ResponseEntity<>(chatService.create(obj), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Chat> update(Chat obj) {
        return new ResponseEntity<>(chatService.update(obj), HttpStatus.OK);
    }

}
