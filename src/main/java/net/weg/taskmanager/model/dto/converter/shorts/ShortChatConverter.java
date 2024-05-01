package net.weg.taskmanager.model.dto.converter.shorts;

import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortChatDTO;

import java.util.Collection;

public class ShortChatConverter implements Converter<ShortChatDTO, Chat> {
    @Override
    public ShortChatDTO convertOne(Chat obj) {
        return obj != null ? new ShortChatDTO(obj) : null;
    }

    @Override
    public Collection<ShortChatDTO> convertAll(Collection<Chat> objs) {
        return null;
    }
}
