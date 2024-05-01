package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.weg.taskmanager.model.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTeamDTO {

    private User creator;
//    private String imageColor;
//    private Collection<User> participants;

}
