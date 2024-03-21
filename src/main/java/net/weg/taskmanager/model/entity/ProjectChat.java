package net.weg.taskmanager.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.abstracts.Chat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectChat extends Chat {

    @OneToOne(optional = false, mappedBy = "chat")
    @JoinColumn(updatable = false, unique = true)
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Project project;

}
