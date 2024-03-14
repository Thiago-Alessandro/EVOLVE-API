package net.weg.taskmanager.model.dto.get;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.property.Property;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectDTO {

    private Long id;
    private String name;
    private String description;
    private File image;
    private String imageColor;
    private User creator;
    private LocalDate finalDate;
    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;

    //vai continuar msm?
    private Collection<User> administrators;
    private Collection<Property> properties;
    private Collection<Status> statusList;
    private Collection<User> members;
    private Team team;

    private ProjectChat chat;
    private Collection<GetTaskDTO> tasks;

}
