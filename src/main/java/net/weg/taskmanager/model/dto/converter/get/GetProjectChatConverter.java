package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetProjectChatDTO;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.entity.ProjectChat;
import net.weg.taskmanager.model.entity.TeamChat;

import java.util.Collection;

public class GetProjectChatConverter implements Converter<GetProjectChatDTO, ProjectChat> {

    @Override
    public GetProjectChatDTO convertOne(ProjectChat obj) {
        return obj != null ? new GetProjectChatDTO(obj) : null;
    }

    @Override
    public Collection<GetProjectChatDTO> convertAll(Collection<ProjectChat> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
