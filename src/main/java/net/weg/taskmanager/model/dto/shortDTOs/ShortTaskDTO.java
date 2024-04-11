package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.PriorityRecordConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortProjectConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.record.PriorityRecord;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

//    private Collection<ShortUserDTO> associates;

    private Integer statusListIndex;

    public ShortTaskDTO(Task task){
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        Converter<ShortProjectDTO, Project> projectConverter = new ShortProjectConverter();
        Converter<PriorityRecord, Priority> priorityConverter = new PriorityRecordConverter();
        BeanUtils.copyProperties(task, this);
        this.creator = userConverter.convertOne(task.getCreator());
        this.project = projectConverter.convertOne(task.getProject());
        this.priority = priorityConverter.convertOne(task.getPriority());
    }

}
