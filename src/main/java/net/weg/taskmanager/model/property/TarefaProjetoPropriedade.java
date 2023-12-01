package net.weg.taskmanager.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.Usuario;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TarefaProjetoPropriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Propriedade propriedade;
    @ManyToOne
    @JsonIgnore
    private Tarefa tarefa;
    @ManyToOne
    @JsonIgnore
    private Projeto projeto;

    private String valor;

    @ManyToMany
    private Collection<Text> usuarios;

    @Enumerated()
    private TipoPropriedade tipo;



    //criar getPropriedade na controller do projeto

//    public TarefaProjetoPropriedade(){
//        this.tipo.getPropriedade();
//    }
//    olhar isso aqui amanhã



//    @ManyToMany
//    private Collection<Propriedade> propriedades;

}
