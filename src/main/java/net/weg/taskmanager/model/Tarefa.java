package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String nome;
    private Boolean favoritado;

    //Mudar datas para Date posteriormente
    private String dataFinal;
    private String dataCriacao;

    @ManyToOne
    private Status statusAtual;
    @ManyToMany
    private Collection<Status> statusPossiveis;
    //Poder colocar status como "globais" para o projeto inteiro

    @ManyToOne
    private Usuario criador;
    @ManyToOne
    private Projeto projeto;
    @ManyToMany
    private Collection<Propriedade> propriedades;
    @OneToMany
    private Collection<Subtarefa> subtarefas;

}
