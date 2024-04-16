package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.User;

import java.util.Collection;

public class ShortUserConverter implements Converter<ShortUserDTO, User> {
    @Override
    public ShortUserDTO convertOne(User obj) {
        return obj != null ? new ShortUserDTO(obj) : null;
    }

    @Override
    public Collection<ShortUserDTO> convertAll(Collection<User> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
