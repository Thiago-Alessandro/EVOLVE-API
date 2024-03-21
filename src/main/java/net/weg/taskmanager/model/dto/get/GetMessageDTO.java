package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
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
    private GetUserDTO sender;
    private LocalDateTime date;
    private MessageStatus status;
    private GetChatDTO chat;

    public GetMessageDTO(Message message){
        BeanUtils.copyProperties(message, this);
        this.attachments = DTOUtils.fileToGetFileDTOS(message.getAttachments());
        this.sender = DTOUtils.userToGetUserDTO(message.getSender());
        this.chat = DTOUtils.chatToGetChatDTO(message.getChat());
    }

}
