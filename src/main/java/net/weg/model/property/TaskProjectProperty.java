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
        this.value = this.property.getType().getNewValue();
        ObjectMapper om = new ObjectMapper();
        ValueText value1 = (ValueText) om.convertValue(value, this.value.getClass());
        System.out.println(value1);
        BeanUtils.copyProperties(value1, this.value);
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