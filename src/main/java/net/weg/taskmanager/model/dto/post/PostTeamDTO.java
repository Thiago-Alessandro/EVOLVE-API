package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.User;

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
