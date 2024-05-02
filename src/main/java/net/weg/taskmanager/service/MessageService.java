package net.weg.taskmanager.service;

import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetMessageDTO;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.MessageDTO;
import net.weg.taskmanager.model.enums.MessageStatus;
import net.weg.taskmanager.repository.TeamNotificationRepository;
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
    private final TeamNotificationService  teamNotificationService;

//    private MessageProcessor messageProcessor = new MessageProcessor();

//    @Override
    public Message findById(Long id) {
        return null;
//        return messageRepository.findById(id).get();
    }

    //    @Override
    public Collection<GetMessageDTO> findAll() {
        Collection<Message> messages = messageRepository.findAll();
        messages
                .forEach(message -> MessageProcessor.getInstance().resolveMessage(message));
        return messages.stream().map(GetMessageDTO::new).toList();
    }

    public GetMessageDTO patchMessageStatus(Long id, String status){
        Message message = messageRepository.findById(id).get();
        message.setStatus(MessageStatus.valueOf(status));
        Message savedMessage = messageRepository.save(message);
        MessageProcessor.getInstance().resolveMessage(savedMessage);
        return new GetMessageDTO(savedMessage);
    }

    //    @Override
    public void delete(Long id) {

    }

    //    @Override
    public GetMessageDTO create(MessageDTO obj) {

        Message message = new Message();
        BeanUtils.copyProperties(obj, message);
        message.setChat(chatRepository.findById(obj.getChatId()).get());

        Message newMessage = messageRepository.save(message);
        MessageProcessor.getInstance().resolveMessage(newMessage);
        this.teamNotificationService.receiveMessage(newMessage.getSender().getId(),newMessage.getChat().getUsers());
        return new GetMessageDTO(newMessage);
    }

    public GetMessageDTO update(MessageDTO obj) {
//        System.out.println(obj.getChatId());
        Message message = new Message();
        BeanUtils.copyProperties(obj, message);
        message.setChat(chatRepository.findById(obj.getChatId()).get());

        Message updatedMessage = messageRepository.save(message);
        MessageProcessor.getInstance().resolveMessage(updatedMessage);
        return new GetMessageDTO(updatedMessage);
    }
}
