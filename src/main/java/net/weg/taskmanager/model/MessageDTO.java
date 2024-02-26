package net.weg.taskmanager.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MessageDTO {

    private Long id;

    private String content;

    private User sender;
    private LocalDateTime date;
    private MessageStatus status;

    private Long chatId;

}
