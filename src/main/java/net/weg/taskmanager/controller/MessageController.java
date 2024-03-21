package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.MessageDTO;
import net.weg.taskmanager.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("message")
@AllArgsConstructor
@RestController
public class MessageController{
    private final MessageService messageService;

//    @Override
    public ResponseEntity<MessageDTO> findById(Long id) {
        return null;
    }

//    @Override
    @GetMapping
    public ResponseEntity<Collection<Message>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }

//    @Override
    public ResponseEntity<MessageDTO> delete(Long id) {
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
