package net.weg.taskmanager.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskProjectProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Property property;
    @ManyToOne
    @JsonIgnore
    private Task task;
    @ManyToOne
    @JsonIgnore
    private Project project;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Text> values;

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
