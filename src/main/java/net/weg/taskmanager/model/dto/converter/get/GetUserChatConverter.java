package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.get.GetUserChatDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.UserChat;

import java.util.Collection;

public class GetUserChatConverter implements Converter<GetUserChatDTO, UserChat> {

    @Override
    public GetUserChatDTO convertOne(UserChat obj) {
        return obj != null ? new GetUserChatDTO(obj) : null;
    }

    @Override
    public Collection<GetUserChatDTO> convertAll(Collection<UserChat> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
