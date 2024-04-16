package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetMessageDTO;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.Project;

import java.util.Collection;

public class GetProjectConverter implements Converter<GetProjectDTO, Project> {

    @Override
    public GetProjectDTO convertOne(Project obj) {
        return obj != null ? new GetProjectDTO(obj) : null;
    }

    @Override
    public Collection<GetProjectDTO> convertAll(Collection<Project> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
