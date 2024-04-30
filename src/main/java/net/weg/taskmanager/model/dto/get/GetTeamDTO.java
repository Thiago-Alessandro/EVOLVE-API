package net.weg.taskmanager.model.dto.get;

import lombok.Data;
import net.weg.taskmanager.model.Team;
import org.springframework.beans.BeanUtils;

@Data
public class GetTeamDTO {

    public GetTeamDTO(Team team){
        BeanUtils.copyProperties(team, this);
    }

}
