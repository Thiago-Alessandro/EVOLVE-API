package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;

import java.time.LocalDate;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {

    private String name;
    private String description;
    private String imageColor;
    private User creator;
    private Collection<User> administrators;
    private LocalDate finalDate;
    private Collection<User> members;
    private Team team;
}
