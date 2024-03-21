package net.weg.taskmanager.model.dto.shortDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamChat;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamShortDTO {

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
    private UserShortDTO administrator;
    private Collection<UserShortDTO> participants;

    public TeamShortDTO(Team team){
        BeanUtils.copyProperties(team, this);
        this.administrator = DTOUtils.userToUserShort(team.getAdministrator());
        this.participants = DTOUtils.usersToUserShort(team.getParticipants());
        this.image = DTOUtils.fileToGetFileDTO(team.getImage());
    }

}
