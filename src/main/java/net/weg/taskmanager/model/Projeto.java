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
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String nome;
    private String descricao;
//    @OneToMany(mappedBy = "projeto")
//    private Collection<Tarefa> tarefas;
    @OneToMany(mappedBy = "projeto")
    private Collection<TarefaProjetoPropriedade> propriedades;
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Status> listaStatus;

}
