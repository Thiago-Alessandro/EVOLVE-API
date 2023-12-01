package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamChat extends Chat{

    @OneToOne
    //ao inserir, atualizar ou remover uma equipe terá de ser criado, atualizado ou excuido seu chat
    private Equipe team;

    @ManyToMany()
    @JsonIgnore
    private Collection<Usuario> users;

}
