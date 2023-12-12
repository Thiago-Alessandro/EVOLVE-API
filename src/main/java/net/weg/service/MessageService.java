package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.Message;
import net.weg.model.MessageDTO;
import net.weg.model.ResolveStackOverflow;
import net.weg.repository.ChatRepository;
import net.weg.repository.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
