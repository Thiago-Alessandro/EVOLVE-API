package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserChatDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.service.processor.ChatProcessor;
import net.weg.taskmanager.model.entity.UserChat;
import net.weg.taskmanager.repository.UserChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserChatService
//        implements IService<UserChat>
{

    //para fins de teste
    private final UserRepository userRepository;

    private final UserChatRepository userChatRepository;

//    private final MessageRepository messageRepository;

//    private static Long count = 1;

//    @Override
    public GetUserChatDTO findById(Long id) {
        UserChat chat = userChatRepository.findById(id).get();
        return resolveAndTreat(chat);
    }

//    @Override
    public Collection<GetUserChatDTO> findAll() {
        Collection<UserChat> chats = userChatRepository.findAll();
        return resolveAndTreat(chats);
    }

//    @Override
    public void delete(Long id) {
        userChatRepository.deleteById(id);
    }

//    @Override
    public GetUserChatDTO create(UserChat obj) {
//        validaChat(obj);
        return resolveAndTreat(userChatRepository.save(obj));
    }

//    @Override
    public GetUserChatDTO update(UserChat obj) {
//        validaChat(obj);

        UserChat updatedUserChat = userChatRepository.save(obj);
       return resolveAndTreat(updatedUserChat);
    }

    public Collection<GetUserChatDTO> getChatsByUserId(Long id){
        Collection<UserChat> chats = userChatRepository.findUserChatsByUsersContaining(userRepository.findById(id).get());
        return resolveAndTreat(chats);
    }

    private GetUserChatDTO resolveAndTreat(UserChat chat){
        ChatProcessor.getInstance().resolveChat(chat);
        return new GetUserChatDTO(chat);
    }

    private Collection<GetUserChatDTO> resolveAndTreat(Collection<UserChat> chats){
        return chats.stream().map(this::resolveAndTreat).toList();
    }

//    private void validaChat (Chat chat){
//        if(chat.getType().equals("USER")){
//            if (chat.getMembers().size() != 2){
//                throw new RuntimeException("User chat members number doesn't match excpected (2)");
//            }
//        }
//    }

}
