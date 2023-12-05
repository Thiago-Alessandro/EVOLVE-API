package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
//    @Lob
//    private MultipartFile content;
    // ?????
    @Lob
    @Column(length = 999999999)
    private String content;
    @ManyToOne
    private Usuario sender;
    private Date date;
    private MessageStatus status;

    @ManyToOne
//    @JsonIgnore
    private Chat chat;


}
