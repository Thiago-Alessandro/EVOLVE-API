package net.weg.taskmanager.model.dto.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.File;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTeamDTO {

    private String name;
    private String imageColor;
    private User administrator;
    private Collection<User> participants;

}
