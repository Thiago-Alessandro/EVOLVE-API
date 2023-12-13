package net.weg.controller;

import lombok.AllArgsConstructor;
import net.weg.exception.ExceptionMissingData;
import net.weg.model.Message;
import net.weg.model.MessageDTO;
import net.weg.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("message")
@AllArgsConstructor
@RestController
public class MessageController{
    private final MessageService messageService;

//    @Override
    public ResponseEntity<Message> findById(Integer id) {
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }

//    @Override
    @GetMapping
    public ResponseEntity<Collection<Message>> findAll() {
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

//    @Override
    public ResponseEntity delete(Integer id) {
        try {
            messageService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ExceptionMissingData e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Override
    @PostMapping
    public ResponseEntity<Message> create(@RequestBody MessageDTO obj) {
        return ResponseEntity.ok(messageService.create(obj));
    }

//    @Override
    public ResponseEntity<Message> update(Message message) {
        return new ResponseEntity<>(messageService.update(message), HttpStatus.OK);
    }
}
