package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetCommentDTO;
import net.weg.taskmanager.model.dto.get.GetMessageDTO;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.Message;

import java.util.Collection;

public class GetMessageConverter implements Converter<GetMessageDTO, Message> {

    @Override
    public GetMessageDTO convertOne(Message obj) {
        return obj != null ? new GetMessageDTO(obj) : null;
    }

    @Override
    public Collection<GetMessageDTO> convertAll(Collection<Message> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
