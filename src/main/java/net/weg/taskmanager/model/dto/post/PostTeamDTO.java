package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.User;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTeamDTO {

    private User creator;
//    private String imageColor;
//    private Collection<User> participants;

}
