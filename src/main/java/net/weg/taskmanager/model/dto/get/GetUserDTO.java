package net.weg.taskmanager.model.dto.get;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.File;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.UserChat;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {

    private Long id;
    private String name;
//    private String password;
    private String email;
    private GetFileDTO image;
    private String imageColor;
    private Collection<UserChat> chats;
    private Collection<Task> createdTasks;
    private Collection<Team> managedTeams;
    private Collection<Team> teams;

}
