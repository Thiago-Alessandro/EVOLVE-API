package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;

import java.time.LocalDate;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {

    private String name;
    private String description;
    private String imageColor;
    private File image;
    private User creator;
    private Collection<User> administrators;
    private LocalDate finalDate;
    private Collection<User> members;
    private Collection<Status> statusList;
    private Team team;
}
