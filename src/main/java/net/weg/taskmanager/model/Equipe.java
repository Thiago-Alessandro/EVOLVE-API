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
//    private String descricao;
    @ManyToOne
    private Usuario administrador;
    @OneToMany(mappedBy = "equipe")
    private Collection<Projeto> projetos;
    @ManyToMany()
    @JsonIgnore
    //tirar eventualmente
    private Collection<Usuario> participantes;
    @OneToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
    private TeamChat chat;

}
