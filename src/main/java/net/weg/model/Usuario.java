package net.weg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString()
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String nome;
    private String senha;
    private String email;

    @Lob
    @Column(length = 999999999)
    @ToString.Exclude
    private String fotoPerfil;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @ToString.Exclude
    private Collection<UserChat> chats;

    @OneToMany(mappedBy = "criador")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Tarefa> tarefasCriadas;

    @OneToMany(mappedBy = "administrador")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Equipe> equipesAdministradas;

    @ManyToMany(mappedBy = "participantes")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Equipe> equipes;
}