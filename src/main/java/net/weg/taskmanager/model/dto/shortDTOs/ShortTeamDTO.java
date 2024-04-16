package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Team;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTeamDTO {

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
//    private ShortUserDTO administrator;
//    private Collection<ShortUserDTO> participants;

    public ShortTeamDTO(Team team){
        BeanUtils.copyProperties(team, this);
//        this.administrator = DTOUtils.userToShortUserDTO(team.getAdministrator());
//        this.participants = DTOUtils.usersToShortUserDTO(team.getParticipants());
        this.image = DTOUtils.fileToGetFileDTO(team.getImage());
    }

}
