package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.shortDTOs.ShortChatDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.enums.MessageStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
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
        BeanUtils.copyProperties(message, this);
        this.attachments = DTOUtils.fileToGetFileDTOS(message.getAttachments());
        this.sender = DTOUtils.userToShortUserDTO(message.getSender());
        this.chat = DTOUtils.chatToShortChatDTO(message.getChat());
    }

}
