package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortChatConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortChatDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.enums.MessageStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageDTO {

    private Long id;

    private Collection<GetFileDTO> attachments;
    private String content;
    private ShortUserDTO sender;
    private LocalDateTime date;
    private MessageStatus status;
    private ShortChatDTO chat;

    public GetMessageDTO(Message message){
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        GetFileConverter fileDTOCOnverter = new GetFileConverter();
        Converter<ShortChatDTO, Chat> shortChatConverter = new ShortChatConverter();

        BeanUtils.copyProperties(message, this);
        this.attachments = message.getAttachments() != null ? message.getAttachments().stream().map(attachment -> fileDTOCOnverter.convertOne(attachment, false)).toList() : new ArrayList<>();
        this.sender = shortUserConverter.convertOne(message.getSender());
        this.chat = shortChatConverter.convertOne(message.getChat());
    }

}
