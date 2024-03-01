package net.weg.taskmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer id;


    private String name;
    private Boolean favorited;

    private String finalDate;
    private String creationDate;
    private String schedulingData;
    private String description;

    private Status currentStatus;

    private PriorityRecord priority ;

    private User creator;

    private Project project;

    private Collection<Property> properties;

    private Collection<Subtask> subtasks;

    private Collection<User> associates;

    private Integer statusListIndex;
}
