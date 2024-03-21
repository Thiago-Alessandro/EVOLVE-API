package net.weg.taskmanager.model.entity;

import lombok.Data;
import net.weg.taskmanager.model.enums.MessageStatus;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    private Long id;

    private String content;

    private User sender;
    private LocalDateTime date;
    private MessageStatus status;

    private Long chatId;

}
