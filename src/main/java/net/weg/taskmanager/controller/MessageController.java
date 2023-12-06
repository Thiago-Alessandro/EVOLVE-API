package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("message")
@AllArgsConstructor
@RestController
public class MessageController implements IController<Message>{
    @Override
    public ResponseEntity<Message> findById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Collection<Message>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Message> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Message> create(Message obj) {
        return null;
    }

    @Override
    public ResponseEntity<Message> update(Message obj) {
        return null;
    }
}
