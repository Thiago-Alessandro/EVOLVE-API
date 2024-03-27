package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatDTO {

    private Long id;
    private Collection<GetMessageDTO> messages;
    private Collection<ShortUserDTO> users;

    public GetChatDTO(Chat chat){
        BeanUtils.copyProperties(chat, this);
        this.users = DTOUtils.usersToShortUserDTO(chat.getUsers());
        this.messages = DTOUtils.messageToGetMessageDTOS(chat.getMessages());
    }

}
