package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String imagem;
    @ManyToOne
    private Usuario criador;
    @ManyToMany
    private Collection<Usuario> administradores;
    //mudar para Date
    private String DataFinal;
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    private Collection<TarefaProjetoPropriedade> propriedades;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // teremos status padrao? seriam os mesmos objetos para todos projetos? ou seria instanciado um novo para cada novo projeto?
    private Collection<Status> listaStatus;
    @ManyToMany
    private Collection<Usuario> membros;

    @OneToMany(mappedBy = "projeto")
    @JsonIgnore
    private Collection<Tarefa> tarefas;


    }

