package net.weg.taskmanager.model.dto.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.time.LocalDate;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {

    private String name;
    private String description;
    private String imageColor;
    private User creator;
    private Collection<User> administrators;
    private LocalDate finalDate;
    private Collection<User> members;
    private Team team;

//    //rever
//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
//    private Collection<TaskProjectProperty> properties;
//    //rever
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Collection<Status> StatusList;



}
