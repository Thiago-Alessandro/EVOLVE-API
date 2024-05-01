package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamChat;

import java.util.Collection;

public class ShortTeamConverter implements Converter<ShortTeamDTO, Team> {

    @Override
    public ShortTeamDTO convertOne(Team obj) {
        return obj != null ? new ShortTeamDTO(obj) : null;
    }

    @Override
    public Collection<ShortTeamDTO> convertAll(Collection<Team> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
