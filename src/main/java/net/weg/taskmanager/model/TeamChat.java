package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamChat extends Chat{

    @OneToOne(mappedBy = "chat")
    //ao inserir, atualizar ou remover uma equipe terá de ser criado, atualizado ou excuido seu chat
    private Team team;

}
