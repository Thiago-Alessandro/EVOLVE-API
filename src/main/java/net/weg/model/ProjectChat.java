package net.weg.model;

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
public class ProjectChat extends Chat{

    @OneToOne
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Projeto project;

    @ManyToMany()
    @JsonIgnore
    private Collection<Usuario> users;
}
