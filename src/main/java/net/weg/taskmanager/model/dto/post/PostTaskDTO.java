package net.weg.taskmanager.model.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.TaskProjectProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTaskDTO {

    private String name;

    private LocalDate finalDate;
    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;
    private LocalDate scheduledDate;

//    private LocalDateTime creationDate;
    private String description;

    private Status currentStatus;

    @Enumerated(value = EnumType.ORDINAL)
    private Priority priority;

    private User creator;

    private Project project;

    private Collection<User> associates;

    private Integer statusListIndex;

}
