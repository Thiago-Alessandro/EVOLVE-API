package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

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
    private String content;
    @ManyToOne(optional = false)
    private User sender;
    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();
    @Column(nullable = false)
    private MessageStatus status;

    @ManyToOne(optional = false)
    private Chat chat;

}
