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
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String nome;
    private String imagem;
    @ManyToOne
    private Usuario administrador;
    @OneToMany
    private Collection<Projeto> projetos;
    @ManyToMany(mappedBy = "equipes")
    @JsonIgnore
    private Collection<Usuario> participantes;

}
