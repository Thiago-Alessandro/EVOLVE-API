package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ChatService implements IService<Chat>{

    private final ChatRepository chatRepository;

    @Override
    public Chat findById(Integer id) {
        return chatRepository.findById(id).get();
    }

    @Override
    public Collection<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        chatRepository.deleteById(id);
    }

    @Override
    public Chat create(Chat obj) {
        validaChat(obj);
        return chatRepository.save(obj);
    }

    @Override
    public Chat update(Chat obj) {
        validaChat(obj);
        return chatRepository.save(obj);
    }

    private void validaChat (Chat chat){
        if(chat.getType().equals("USER")){
            if (chat.getMembers().size() != 2){
                throw new RuntimeException("User chat members number doesn't match excpected (2)");
            }
            if(chat.getTeam() != null || chat.getProject() != null){
                throw new RuntimeException("Chat from type 'USER' must only have users related");
            }
        }
        if(chat.getType().equals("TEAM")){
            if(chat.getProject() != null){
                throw new RuntimeException("Chat from type 'TEAM' can't have a project related");
            }
        }
        if(chat.getType().equals("Project")){
            if(chat.getTeam() != null){
                throw new RuntimeException("Chat from type 'PROJECT' can't have a team related");
            }
        }
    }

}
