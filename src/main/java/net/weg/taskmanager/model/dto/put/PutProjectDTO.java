package net.weg.taskmanager.model.dto.put;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.property.Property;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutProjectDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean favorited;
    private File image;
    private String imageColor;
    private LocalDateTime finalDate;
    private Collection<Property> properties;
    private Collection<User> members;
    private Collection<Task> tasks;
    //vai continuar msm?
    private Collection<User> administrators;

//    há uma request especifica para se atualizar um status
//    private Collection<Status> statusList;

}
