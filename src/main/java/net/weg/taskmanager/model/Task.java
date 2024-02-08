package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.util.Collection;

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

    @Enumerated(value = EnumType.ORDINAL)
    private Priority priority;

//    @ManyToMany(cascade = CascadeType.ALL)
//    private Collection<Status> statusPossiveis;
//    Poder colocar status como "globais" para o projeto inteiro

    @ManyToOne
    private User creator;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy = "task", cascade = CascadeType.MERGE)
    private Collection<TaskProjectProperty> properties;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Subtask> subtasks;
    @ManyToMany
    private Collection<User> associates;

    private Integer statusListIndex;

}
