package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Task;

import java.util.Collection;

public class GetTaskConverter implements Converter<GetTaskDTO, Task> {

    @Override
    public GetTaskDTO convertOne(Task obj) {
        return obj != null ? new GetTaskDTO(obj) : null;
    }

    @Override
    public Collection<GetTaskDTO> convertAll(Collection<Task> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
