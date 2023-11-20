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
    private String fotoPerfil;

    @OneToMany(mappedBy = "criador")
    @JsonIgnore
    private Collection<Tarefa> tarefasCriadas;
    @OneToMany(mappedBy = "administrador")
    @JsonIgnore
    private Collection<Equipe> equipesAdministradas;
    @ManyToMany
    private Collection<Equipe> equipes;

}
