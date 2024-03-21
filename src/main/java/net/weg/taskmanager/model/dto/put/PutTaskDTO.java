package net.weg.taskmanager.model.dto.put;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.weg.taskmanager.model.dto.get.ShortUserDTO;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Subtask;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutTaskDTO {

    private Long id;
    private String name;
    private Boolean favorited;

    private String finalDate;
    private LocalDate scheduledDate;
    private String description;

    private Status currentStatus;

    private PriorityRecord priority ;

    private Collection<Property> properties;

    private Collection<Subtask> subtasks;

    private Collection<ShortUserDTO> associates;

    private Integer statusListIndex;

}
