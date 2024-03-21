package net.weg.taskmanager.model.dto.get;

import net.weg.taskmanager.model.dto.shortDTOs.TeamShortDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.TeamChat;
import org.springframework.beans.BeanUtils;

public class GetTeamChatDTO extends GetChatDTO{
    private TeamShortDTO getTeamDTO;
    public GetTeamChatDTO(TeamChat teamChat){
        super(teamChat);
        this.getTeamDTO = DTOUtils.teamToShortTeamDTO(teamChat.getTeam());
    }

}
