package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortMessageDTO;
import net.weg.taskmanager.model.entity.Message;

import java.util.Collection;

public class ShortMessageConverter implements Converter<ShortMessageDTO, Message> {

    @Override
    public ShortMessageDTO convertOne(Message obj) {
        return obj != null ? new ShortMessageDTO(obj) : null;
    }

    @Override
    public Collection<ShortMessageDTO> convertAll(Collection<Message> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
