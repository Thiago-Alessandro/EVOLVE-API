package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.util.Collection;
import java.util.HashSet;

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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<TaskProjectProperty> propriedades;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // teremos status padrao? seriam os mesmos objetos para todos projetos? ou seria instanciado um novo para cada novo projeto?
    private Collection<Status> listaStatus;
    @ManyToMany
    private Collection<Usuario> membros;
    @ManyToOne
    @JsonIgnore
    private Equipe equipe;

    @OneToOne()
//    @JsonIgnore
    private Chat chat;

    @OneToMany(mappedBy = "projeto")
//    @JsonIgnore
    private Collection<Tarefa> tarefas;

    public void setStatusPadrao() {
        Collection<Status> statusPadrao = new HashSet<>();
        statusPadrao.add(new Status("pendente", "#7CD5F4", "#000000",true));
        statusPadrao.add(new Status("em progresso", "#FCEC62", "#000000",true));
        statusPadrao.add(new Status("concluido", "#86C19F", "#000000",true));
        statusPadrao.add(new Status("não atribuido", "#9CA3AE", "#000000",true));
        if(this.getListaStatus()!=null){
            this.getListaStatus().addAll(statusPadrao);
        } else{
            this.setListaStatus((statusPadrao));
        }
    }
}