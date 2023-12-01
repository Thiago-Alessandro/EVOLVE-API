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
    private String nome;
    private String senha;
    private String email;
    @Lob
    @Column(length = 999999999)
    private String testeImagem;
    private String fotoPerfil;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Collection<UserChat> chats;

    @OneToMany(mappedBy = "criador")
    @JsonIgnore
    private Collection<Tarefa> tarefasCriadas;
    @OneToMany(mappedBy = "administrador")
    @JsonIgnore
    private Collection<Equipe> equipesAdministradas;
    @ManyToMany(mappedBy = "participantes")
    //tava na equipe
    @JsonIgnore
    private Collection<Equipe> equipes;

}
