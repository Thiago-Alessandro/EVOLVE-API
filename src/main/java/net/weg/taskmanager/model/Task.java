package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private Boolean favorited;

    //Mudar datas para Date posteriormente
    private String finalDate;
    private String creationDate;
    private String description;

    @ManyToOne()
    private Status currentStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Priority> priorities ;

//    @ManyToMany(cascade = CascadeType.ALL)
//    private Collection<Status> statusPossiveis;
//    Poder colocar status como "globais" para o projeto inteiro

    @ManyToOne
    private User creator;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Collection<TaskProjectProperty> properties;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Subtask> subtasks;
    @ManyToMany
    private Collection<User> associates;

    private Integer statusListIndex;

    public void setStandardPriorities() {

            Collection<Priority> standardPriorities = new HashSet<>();
            standardPriorities.add(new Priority("nenhuma","#cccccc"));
            standardPriorities.add(new Priority("muito baixa","#6bbcfa"));
            standardPriorities.add(new Priority("baixa","#4db339"));
            standardPriorities.add(new Priority("media","#f5e020"));
            standardPriorities.add(new Priority("alta","#f57520"));
            standardPriorities.add(new Priority("urgente","#e32910"));
            if (this.getPriorities() != null) {
                this.getPriorities().addAll(standardPriorities);
            } else{
                this.setPriorities((standardPriorities));
            }

    }

}
