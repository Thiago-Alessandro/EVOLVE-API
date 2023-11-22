package net.weg.taskmanager.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Tarefa;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TarefaProjetoPropriedade {

    @Id
    private Integer id;

    @ManyToOne
    private Propriedade propriedade;
    @ManyToOne
    private Tarefa tarefa;
    @ManyToOne
    private Projeto projeto;

    private String valor;

    private Enum tipo;

//    TarefaProjetoPropriedade(){
//        Propriedade.builder().
//    }
//    olhar isso aqui amanhã

//    @ManyToMany
//    private Collection<Propriedade> propriedades;

}
