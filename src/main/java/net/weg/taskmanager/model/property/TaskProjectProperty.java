package net.weg.taskmanager.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TaskProjectProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Property property;
    @ManyToOne
    @JsonIgnore
    private Tarefa task;
    @ManyToOne
    @JsonIgnore
    private Projeto project;

    @ManyToMany
    private Collection<Text> value;

    @Enumerated()
    private PropertyType type;


//criar getPropriedade na controller do projeto

//    public TarefaProjetoPropriedade(){
//        this.tipo.getPropriedade();
//    }
//    olhar isso aqui amanhã



//    @ManyToMany
//    private Collection<Propriedade> propriedades;

}
