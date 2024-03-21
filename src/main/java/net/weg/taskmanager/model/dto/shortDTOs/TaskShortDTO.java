package net.weg.taskmanager.model.dto.shortDTOs;

import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Subtask;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class TaskShortDTO {

    private Long id;
    private String name;
    private Boolean favorited;
    private LocalDate finalDate;
    private LocalDate creationDate;
    private LocalDate scheduledDate;
    private LocalDateTime lastTimeEdited;
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
