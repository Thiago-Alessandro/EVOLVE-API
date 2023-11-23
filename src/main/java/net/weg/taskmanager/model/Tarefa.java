package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Propriedade;
import net.weg.taskmanager.model.property.TarefaProjetoPropriedade;

import java.util.Collection;

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
    private String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    //não é mais cascade persist, esta aqui apenas para fins de testes
    private Status statusAtual;

    @Enumerated(value = EnumType.ORDINAL)
    private Prioridade prioridade;

//    @ManyToMany(cascade = CascadeType.ALL)
//    private Collection<Status> statusPossiveis;
//    Poder colocar status como "globais" para o projeto inteiro

    @ManyToOne
    private Usuario criador;
    @ManyToOne
    private Projeto projeto;
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL)
    private Collection<TarefaProjetoPropriedade> propriedades;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Subtarefa> subtarefas;
    @ManyToMany
    private Collection<Usuario> associados;

}
