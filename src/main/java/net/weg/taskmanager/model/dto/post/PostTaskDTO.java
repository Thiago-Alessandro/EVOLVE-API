package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTaskDTO {

    private String name;
    private LocalDateTime finalDate;
    private LocalDateTime scheduledDate;
    private String description;
    private Status currentStatus;
    private PriorityRecord priority ;
    private User creator;
    private Project project;
    private Collection<User> associates;
    private Integer statusListIndex;
    private Collection<Property> properties;

}
