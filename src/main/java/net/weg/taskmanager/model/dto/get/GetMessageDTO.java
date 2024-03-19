package net.weg.taskmanager.model.dto.get;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.model.File;
import net.weg.taskmanager.model.MessageStatus;
import net.weg.taskmanager.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageDTO {

    private Long id;

    private Collection<File> attachments;
    private String content;
    private GetUserDTO sender;
    private LocalDateTime date;
    private MessageStatus status;
    private Chat chat;

}
