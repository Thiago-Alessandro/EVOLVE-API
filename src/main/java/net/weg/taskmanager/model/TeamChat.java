package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamChat extends Chat{

    @OneToOne(optional = false, mappedBy = "chat")
    @JoinColumn(updatable = false, unique = true)
    //ao inserir, atualizar ou remover uma equipe terá de ser criado, atualizado ou excuido seu chat
    private Team team;

    @Override
    public String toString() {
        return "TeamChat{" +
//                "team=" + team +
                "Users=" + getUsers() +
                '}';
    }
}
