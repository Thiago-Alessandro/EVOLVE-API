package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
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
//    @JsonIgnore
    private Collection<GetCommentDTO> comments;
    private LocalDate scheduledDate;
    private LocalDateTime lastTimeEdited;
    private String description;

    private Status currentStatus;

    private PriorityRecord priority ;
//    @JsonIgnore
    private Collection<Historic> historic;

//    @JsonIgnore
    private ShortUserDTO creator;
//    @JsonIgnore
    private ShortProjectDTO project;
//    @JsonIgnore
    private Collection<Property> properties;
//    @JsonIgnore
    private Collection<Subtask> subtasks;
//    @JsonIgnore
    private Collection<ShortUserDTO> associates;

    private Integer statusListIndex;
    private Double progress;

    public GetTaskDTO(Task task){
        BeanUtils.copyProperties(task, this);
        this.creator = DTOUtils.userToShortUserDTO(task.getCreator());
        this.associates = DTOUtils.usersToShortUserDTO(task.getAssociates());
        this.project = DTOUtils.projectToShortProjectDTO(task.getProject());
        this.priority = DTOUtils.priorityToPriorityRecord(task.getPriority());
        this.comments = task.getComments() != null ? task.getComments().stream().map(GetCommentDTO::new).toList() : null;
    }

}
