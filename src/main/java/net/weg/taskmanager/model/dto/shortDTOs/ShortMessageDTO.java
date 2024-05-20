package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.enums.MessageStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageDTO {

    private Long id;

    private Collection<GetFileDTO> attachments;
    private String content;
    private ShortUserDTO sender;
    private LocalDateTime date;
    private MessageStatus status;

    public ShortMessageDTO(Message message){
        GetFileConverter fileConverter = new GetFileConverter();
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        BeanUtils.copyProperties(message, this);
        this.attachments = message.getAttachments() != null ? message.getAttachments().stream().map(attachment -> fileConverter.convertOne(attachment, false)).toList() : new ArrayList<>();
        this.sender = userConverter.convertOne(message.getSender());
    }

}
