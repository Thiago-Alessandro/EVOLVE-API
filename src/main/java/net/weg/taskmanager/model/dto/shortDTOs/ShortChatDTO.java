package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortMessageConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortChatDTO {

    private Long id;
    private Collection<ShortMessageDTO> messages;
    private Collection<ShortUserDTO> users;


    public ShortChatDTO(Chat chat){
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        Converter<ShortMessageDTO, Message> messageDTOConverter = new ShortMessageConverter();
        BeanUtils.copyProperties(chat, this);
        this.users = userConverter.convertAll(chat.getUsers());
        this.messages = messageDTOConverter.convertAll(chat.getMessages());
    }

}
