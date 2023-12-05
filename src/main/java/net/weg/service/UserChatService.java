package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.UserChat;
import net.weg.repository.UserChatRepository;
import net.weg.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserChatService implements IService<UserChat> {
    private final UsuarioRepository userRepository;
    private final UserChatRepository userChatRepository;

    @Override
    public UserChat findById(Integer id) {
        return userChatRepository.findById(id).get();
    }

    @Override
    public Collection<UserChat> findAll() {
        return userChatRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        userChatRepository.deleteById(id);
    }

    @Override
    public UserChat create(UserChat obj) {
//        validaChat(obj);
        return userChatRepository.save(obj);
    }

    @Override
    public UserChat update(UserChat obj) {
//        validaChat(obj);

//        for(Message message : obj.getMessages()){
//            if(message.getId() == null || message.getId() == 0){
//                message.setId(count);
//            message = messageRepository.save(message);
//            message.setChat(obj);
//            System.out.println(message);
//        }

        return userChatRepository.save(obj);
    }

    //teste pra setar id's diferentes em cada mensagem, com verificação de ponta
//    public void teste(UserChat obj) {
//        try {
//            for (Message message : obj.getMessages()) {
//                for (int um = 0; um == obj.getMessages().size(); um++) {
//                    for (Message messageFor : obj.getMessages()) {
//                        if (message.getId() == null) {
//                            message.setId(message.getId() + 1);
//                            if (message.getId() == messageFor.getId()) {
//                                message.setId(message.getId() + 1);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (NullPointerException e) {
//            return;
//        }
//    }

    public Collection<UserChat> getChatsByUserId(Integer id) {
        return userChatRepository.findUserChatsByUsersContaining(userRepository.findById(id).get());
    }

//    private void validaChat (Chat chat){
//        if(chat.getType().equals("USER")){
//            if (chat.getMembers().size() != 2){
//                throw new RuntimeException("User chat members number doesn't match excpected (2)");
//            }
//            if(chat.getTeam() != null || chat.getProject() != null){
//                throw new RuntimeException("Chat from type 'USER' must only have users related");
//            }
//        }
//        if(chat.getType().equals("TEAM")){
//            if(chat.getProject() != null){
//                throw new RuntimeException("Chat from type 'TEAM' can't have a project related");
//            }
//        }
//        if(chat.getType().equals("PROJECT")){
//            if(chat.getTeam() != null){
//                throw new RuntimeException("Chat from type 'PROJECT' can't have a team related");
//            }
//        }
//    }

}