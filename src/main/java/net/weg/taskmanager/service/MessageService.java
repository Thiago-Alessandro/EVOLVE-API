package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.repository.MessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@Service
public class MessageService implements IService<Message> {

    private final MessageRepository messageRepository;

    @Override
    public Message findById(Integer id) {
        return null;
    }

    @Override
    public Collection<Message> findAll() {
        return setMessagesChatNull(messageRepository.findAll());
    }

    private Collection<Message> setMessagesChatNull(Collection<Message> messages){
        Collection<Message> resolvedMessages = new HashSet<>();
        for(Message message:messages){
            message.setChat(null);
            resolvedMessages.add(message);
        }
        return resolvedMessages;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Message create(Message obj) {
        return messageRepository.save(obj);
    }

    @Override
    public Message update(Message obj) {
        return messageRepository.save(obj);
    }
}
