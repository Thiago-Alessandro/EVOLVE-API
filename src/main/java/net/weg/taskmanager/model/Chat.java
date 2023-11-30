package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany()
    @JsonIgnore
    private Collection<Usuario> members;

    @OneToOne
    //ao inserir, atualizar ou remover uma equipe terá de ser criado, atualizado ou excuido seu chat
    private Equipe team;
    @OneToOne
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Projeto project;

    @Enumerated(value = EnumType.ORDINAL)
    private ChatType type;

    @OneToMany()
    private Collection<Message> messages;

}
