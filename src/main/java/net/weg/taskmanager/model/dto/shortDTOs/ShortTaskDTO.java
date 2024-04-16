package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.record.PriorityRecord;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTaskDTO {

    private Long id;


    private String name;
    private Boolean favorited;

//    private LocalDate finalDate;
//    private LocalDate creationDate;
    private LocalDate scheduledDate;
    private LocalDateTime lastTimeEdited;
//    private String description;

    private Status currentStatus;

    private PriorityRecord priority ;


    private ShortUserDTO creator;

    private ShortProjectDTO project;

//    private Collection<Property> properties;

//    private Collection<Subtask> subtasks;

    private Collection<ShortUserDTO> associates;

    private Integer statusListIndex;

    public ShortTaskDTO(Task task){
        BeanUtils.copyProperties(task, this);
        this.creator = DTOUtils.userToShortUserDTO(task.getCreator());
        this.project = DTOUtils.projectToShortProjectDTO(task.getProject());
        this.priority = DTOUtils.priorityToPriorityRecord(task.getPriority());
        this.associates = DTOUtils.usersToShortUserDTO(task.getAssociates());
    }

}
