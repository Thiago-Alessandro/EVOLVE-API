package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.repository.MessageRepository;
import net.weg.taskmanager.repository.UserChatRepository;
import net.weg.taskmanager.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserChatService implements IService<UserChat>{

    //para fins de teste
    private final UsuarioRepository userRepository;

    private final UserChatRepository userChatRepository;

    private final MessageRepository messageRepository;

    private static Integer count = 1;

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
//                count++;
//            }
//            message = messageRepository.save(message);
//            message.setChat(obj);
//            System.out.println(message);
//        }


        return userChatRepository.save(obj);
    }

    public Collection<UserChat> getChatsByUserId(Integer id){
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
