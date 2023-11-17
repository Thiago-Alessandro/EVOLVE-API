package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String email;
    private String senha;
    private String nome;

    @OneToMany(mappedBy = "criador")
    @JsonIgnore
    private Collection<Tarefa> tarefasCriadas;
    @OneToMany(mappedBy = "lider")
    @JsonIgnore
    private Collection<Equipe> equipesLideradas;

}
