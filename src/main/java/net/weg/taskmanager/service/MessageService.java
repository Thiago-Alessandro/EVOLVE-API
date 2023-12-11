package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.MessageDTO;
import net.weg.taskmanager.model.ResolveStackOverflow;
import net.weg.taskmanager.repository.ChatRepository;
import net.weg.taskmanager.repository.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@Service
public class MessageService{

    private final MessageRepository messageRepository;
private final ChatRepository chatRepository;
//    @Override
    public Message findById(Integer id) {
        return null;
    }

//    @Override
    public Collection<Message> findAll() {
        return setMessagesChatNull(messageRepository.findAll());
    }

    private Collection<Message> setMessagesChatNull(Collection<Message> messages){
        for(Message message:messages){
            message.setChat(null);
        }
        return messages;
    }

//    @Override
    public void delete(Integer id) {

    }

//    @Override
    public Message create(MessageDTO obj) {
        System.out.println(obj);
        Message message = new Message();

        BeanUtils.copyProperties(obj, message);
//        System.out.println(message);

        message.setChat(chatRepository.findById(obj.getChatId()).get());
//        System.out.println(message);

        return ResolveStackOverflow.getObjectWithoutStackOverflow(messageRepository.save(message));
    }

//    @Override
    public Message update(Message obj) {
        return messageRepository.save(obj);
    }
}
