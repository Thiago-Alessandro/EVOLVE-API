package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Property;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String description;
    private String image;
    @ManyToOne
    private User creator;
    @ManyToMany
    private Collection<User> administrators;
    //mudar para Date
    private String FinalDate;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<Property> properties;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // teremos status padrao? seriam os mesmos objetos para todos projetos? ou seria instanciado um novo para cada novo projeto?
    private Collection<Status> StatusList;
    @ManyToMany
    private Collection<User> members;
    @ManyToOne
    @JsonIgnore
    private Team team;

    @OneToOne()
//    @JsonIgnore
    private Chat chat;

    @OneToMany(mappedBy = "project")

    private Collection<Task> tasks;

    public void setStandardStatus() {
        Collection<Status> statusPadrao = new HashSet<>();
        statusPadrao.add(new Status("pendente", "#7CD5F4", "#000000",true));
        statusPadrao.add(new Status("em progresso", "#FCEC62", "#000000",true));
        statusPadrao.add(new Status("concluido", "#86C19F", "#000000",true));
        statusPadrao.add(new Status("não atribuido", "#9CA3AE", "#000000",true));
        if(this.getStatusList()!=null){
            this.getStatusList().addAll(statusPadrao);
        } else{
            this.setStatusList((statusPadrao));
        }
    }
}