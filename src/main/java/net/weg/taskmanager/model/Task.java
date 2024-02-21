package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name = "Sem Nome";
    @NonNull
    private Boolean favorited;

//    private LocalDateTime finalDate;
//    private LocalDateTime creationDate = LocalDateTime.now();
    private String finalDate;
    private String creationDate;
    private String description;

    @ManyToOne()
    private Status currentStatus;

    @Enumerated(value = EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Collection<TaskProjectProperty> properties;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Subtask> subtasks;

    @ManyToMany()
    @Column(nullable = false)
    private Collection<User> associates;

    private Integer statusListIndex;

}
