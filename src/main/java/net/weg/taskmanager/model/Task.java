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
    private String FinalDate;
    private String CreationDate;
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
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Collection<TaskProjectProperty> properties;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<SubTask> subTasks;
    @ManyToMany
    private Collection<User> associates;

    private Integer statusListIndex;

}
