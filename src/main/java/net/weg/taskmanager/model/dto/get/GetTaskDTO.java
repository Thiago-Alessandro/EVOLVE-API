package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskDTO {
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


    private GetUserDTO creator;

    private GetProjectDTO project;

    private Collection<Property> properties;

    private Collection<Subtask> subtasks;

    private Collection<GetUserDTO> associates;

    private Integer statusListIndex;

    public GetTaskDTO(Task task){
        BeanUtils.copyProperties(task, this);
        this.creator = DTOUtils.userToGetUserDTO(task.getCreator());
        this.associates = DTOUtils.usersToGetUserDTOs(task.getAssociates());
        this.project = DTOUtils.projectToGetProjectDTO(task.getProject());
        this.priority = DTOUtils.priorityToPriorityRecord(task.getPriority());
    }

}
