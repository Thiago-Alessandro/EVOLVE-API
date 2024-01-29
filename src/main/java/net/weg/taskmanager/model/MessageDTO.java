package net.weg.taskmanager.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

    private Integer id;

    private String content;

    private User sender;
    private Date date;
    private MessageStatus status;

    private Integer chatId;

}
