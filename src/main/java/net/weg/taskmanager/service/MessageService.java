package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.MessageDTO;
import net.weg.taskmanager.service.processor.ChatProcessor;
import net.weg.taskmanager.service.processor.MessageProcessor;
import net.weg.taskmanager.repository.ChatRepository;
import net.weg.taskmanager.repository.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

//@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

//    private MessageProcessor messageProcessor = new MessageProcessor();

//    @Override
    public Message findById(Long id) {
        return null;
//        return messageRepository.findById(id).get();
    }

    //    @Override
    public Collection<Message> findAll() {
        Collection<Message> messages = messageRepository.findAll();
        messages.stream()
                .forEach(message -> MessageProcessor.getInstance().resolveMessage(message));
        return messages;
    }

//    private Collection<Message> setMessagesChatNull(Collection<Message> messages) {
//        for (Message message : messages) {
//            message.setChat(null);
//        }
//        return messages;
//    }

    //    @Override
    public void delete(Long id) {

    }

    //    @Override
    public Message create(MessageDTO obj) {

        Message message = new Message();

        BeanUtils.copyProperties(obj, message);


        message.setChat(chatRepository.findById(obj.getChatId()).get());

        Message newMessage = messageRepository.save(message);
        MessageProcessor.getInstance().resolveMessage(newMessage);
        return newMessage;
    }

    //    @Override
    public Message update(Message obj) {
        return messageRepository.save(obj);
    }
}
