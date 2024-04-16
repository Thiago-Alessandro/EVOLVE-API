package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;

import java.util.Collection;

public class GetTeamConverter implements Converter<GetTeamDTO, Team> {

    @Override
    public GetTeamDTO convertOne(Team obj) {
        return obj != null ? new GetTeamDTO(obj) : null;
    }

    @Override
    public Collection<GetTeamDTO> convertAll(Collection<Team> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
