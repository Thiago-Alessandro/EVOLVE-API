package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.TeamChat;

import java.util.Collection;

public class GetTeamChatConverter implements Converter<GetTeamChatDTO, TeamChat> {
    @Override
    public GetTeamChatDTO convertOne(TeamChat obj) {
        return obj != null ? new GetTeamChatDTO(obj) : null;
    }

    @Override
    public Collection<GetTeamChatDTO> convertAll(Collection<TeamChat> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList(): null;
    }
}
