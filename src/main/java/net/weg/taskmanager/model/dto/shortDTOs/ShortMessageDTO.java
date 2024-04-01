package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.enums.MessageStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
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
        BeanUtils.copyProperties(message, this);
        this.attachments = DTOUtils.fileToGetFileDTOS(message.getAttachments());
        this.sender = DTOUtils.userToShortUserDTO(message.getSender());
    }

}
