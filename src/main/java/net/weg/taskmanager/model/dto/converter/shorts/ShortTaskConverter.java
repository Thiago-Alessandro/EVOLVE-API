package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.entity.Task;

import java.util.Collection;

public class ShortTaskConverter implements Converter<ShortTaskDTO, Task> {
    @Override
    public ShortTaskDTO convertOne(Task obj) {
        return obj != null ? new ShortTaskDTO(obj) : null;
    }

    @Override
    public Collection<ShortTaskDTO> convertAll(Collection<Task> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
