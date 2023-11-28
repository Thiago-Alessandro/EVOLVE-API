package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @OneToOne
    private Usuario user;
    @OneToOne
    private Equipe team;
    @OneToOne
    private Projeto project;
    @OneToMany
    private Collection<Message> messages;

}
