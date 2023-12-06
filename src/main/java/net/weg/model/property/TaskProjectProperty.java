package net.weg.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.model.Projeto;
import net.weg.model.Tarefa;
import org.springframework.beans.BeanUtils;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Value value;

    //    @Override()
    //estava tipado com Object
    public void setValue(Object value) {
        System.out.println("Acessou o set em TaskProjectProperty");
        ObjectMapper om = new ObjectMapper();
        this.value = om.convertValue(value, this.property.getType().getNewValue());
        System.out.println(this.value);
    }

//    public TaskProjectProperty(Property property, Tarefa task, Projeto project, Value value) {
//        this.property = property;
//        this.task = task;
//        this.project = project;
//        setValue(value);
//    }


    //criar getPropriedade na controller do projeto

//    public TarefaProjetoPropriedade(){
//        this.tipo.getPropriedade();
//    }
//    olhar isso aqui amanhã


//    @ManyToMany
//    private Collection<Propriedade> propriedades;

}