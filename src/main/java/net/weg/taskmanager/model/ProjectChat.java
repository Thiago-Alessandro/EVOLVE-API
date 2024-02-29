package net.weg.taskmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectChat extends Chat{

    @OneToOne(optional = false, mappedBy = "chat")
    @JoinColumn(updatable = false, unique = true)
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Project project;

}
