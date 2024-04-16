package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetMessageConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.User;
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
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        Converter<GetMessageDTO, Message> messageDTOConverter = new GetMessageConverter();
        BeanUtils.copyProperties(chat, this);
        this.users = shortUserConverter.convertAll(chat.getUsers());
        this.messages = messageDTOConverter.convertAll(chat.getMessages());
    }

}
