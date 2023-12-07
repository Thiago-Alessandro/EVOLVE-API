package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

    private Integer id;

    private String content;

    private Usuario sender;
    private Date date;
    private MessageStatus status;

    private Integer chatId;

}
