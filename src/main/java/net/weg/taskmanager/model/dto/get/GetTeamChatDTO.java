package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTeamConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamChat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamChatDTO extends GetChatDTO{
    private ShortTeamDTO team;
    public GetTeamChatDTO(TeamChat teamChat){
        super(teamChat);
        Converter<ShortTeamDTO, Team> shortTeamConverter = new ShortTeamConverter();
        this.team = shortTeamConverter.convertOne(teamChat.getTeam());
    }

}
