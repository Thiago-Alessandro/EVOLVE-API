package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {

//    private String name;
//    private String description;
//    private String imageColor;
//    private LocalDateTime finalDate;
//    private Collection<UserProject> members;
    private User creator;
    private Team team;


    private String name;
    private String description;
    private String imageColor;
    private File image;
    private LocalDate finalDate;
    private Collection<UserProject> members;
    private Collection<Status> statusList;

}
