package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortChatDTO {

    private Long id;
    private Collection<ShortMessageDTO> messages;
    private Collection<ShortUserDTO> users;


    public ShortChatDTO(Chat chat){
        BeanUtils.copyProperties(chat, this);
        this.users = DTOUtils.usersToShortUserDTO(chat.getUsers());
        this.messages = DTOUtils.messageToShortMessageDTOS(chat.getMessages());
    }

}
