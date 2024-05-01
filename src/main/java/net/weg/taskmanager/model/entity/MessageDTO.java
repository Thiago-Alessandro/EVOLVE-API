package net.weg.taskmanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.weg.taskmanager.model.enums.MessageStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {

    private Long id;

    private String content;
    @ToString.Exclude
    private User sender;
    private LocalDateTime date;
    private MessageStatus status;

    private Long chatId;

}
