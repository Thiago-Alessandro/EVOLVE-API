package net.weg.taskmanager.model.dto.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {

    private String name;
    private String description;
    private String image;

    @ManyToOne
    private User creator;

    @ManyToMany
    private Collection<User> administrators;

    private String FinalDate;
    //rever
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<TaskProjectProperty> properties;
    //rever
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Status> StatusList;

    @ManyToMany
    private Collection<User> members;

    @ManyToOne
    private Team team;

}
