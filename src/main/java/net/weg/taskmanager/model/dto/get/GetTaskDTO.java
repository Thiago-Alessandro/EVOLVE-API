package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
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
    private Collection<GetCommentDTO> comments;
    private LocalDate scheduledDate;
    private LocalDateTime lastTimeEdited;
    private String description;

    private Status currentStatus;

    private PriorityRecord priority ;


    private ShortUserDTO creator;

    private ShortProjectDTO project;

    private Collection<Property> properties;

    private Collection<Subtask> subtasks;

    private Collection<ShortUserDTO> associates;

    private Integer statusListIndex;
    private Double progress;

    public GetTaskDTO(Task task){
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        BeanUtils.copyProperties(task, this);
        this.creator = shortUserConverter.convertOne(task.getCreator());
        this.associates = shortUserConverter.convertAll(task.getAssociates());
        this.project = DTOUtils.projectToShortProjectDTO(task.getProject());
        this.priority = DTOUtils.priorityToPriorityRecord(task.getPriority());
        this.comments = task.getComments() != null ? task.getComments().stream().map(GetCommentDTO::new).toList() : null;
    }

}
