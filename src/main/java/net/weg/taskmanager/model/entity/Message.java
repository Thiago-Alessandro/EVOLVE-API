package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGBLOB")
    @OneToMany()
    private Collection<File> attachments;
    @Column(length = 1000)
    private String content;
    @ManyToOne(optional = false)
    private User sender;
    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @ManyToOne(optional = false)
    private Chat chat;

}
