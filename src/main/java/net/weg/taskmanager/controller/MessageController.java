package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.MessageDTO;
import net.weg.taskmanager.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("message")
@AllArgsConstructor
@RestController
public class MessageController{
    private final MessageService messageService;

//    @Override
    public ResponseEntity<MessageDTO> findById(Integer id) {
        return null;
    }

//    @Override
    @GetMapping
    public ResponseEntity<Collection<Message>> findAll() {
        return null;
    }

//    @Override
    public ResponseEntity<MessageDTO> delete(Integer id) {
        return null;
    }

//    @Override
    @PostMapping
    public ResponseEntity<Message> create(@RequestBody MessageDTO obj) {
        return ResponseEntity.ok(messageService.create(obj));
    }

//    @Override
    public ResponseEntity<MessageDTO> update(MessageDTO obj) {
        return null;
    }
}
