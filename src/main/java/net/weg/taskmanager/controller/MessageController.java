package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetMessageDTO;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.MessageDTO;
import net.weg.taskmanager.model.enums.MessageStatus;
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
    public ResponseEntity<Collection<GetMessageDTO>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }

//    @Override
    public ResponseEntity<MessageDTO> delete(Long id) {
        return null;
    }

//    @Override
    @PostMapping
    public ResponseEntity<GetMessageDTO> create(@RequestBody MessageDTO obj) {
        return ResponseEntity.ok(messageService.create(obj));
    }

    @PatchMapping("/{id}/{newMessageStatus}")
    public GetMessageDTO patchMessageStatus(@PathVariable Long id, @PathVariable String newMessageStatus){
        return messageService.patchMessageStatus(id, newMessageStatus);
    }

//    @PutMapping
//    public ResponseEntity<GetMessageDTO> update(MessageDTO obj) {
//        //não estava funcionando
//        return ResponseEntity.ok(messageService.update(obj));
//    }
}
