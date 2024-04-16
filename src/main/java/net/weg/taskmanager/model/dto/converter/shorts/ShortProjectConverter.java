package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortMessageDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.Project;

import java.util.Collection;

public class ShortProjectConverter implements Converter<ShortProjectDTO, Project> {

    @Override
    public ShortProjectDTO convertOne(Project obj) {
        return obj != null ? new ShortProjectDTO(obj) : null;
    }

    @Override
    public Collection<ShortProjectDTO> convertAll(Collection<Project> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
