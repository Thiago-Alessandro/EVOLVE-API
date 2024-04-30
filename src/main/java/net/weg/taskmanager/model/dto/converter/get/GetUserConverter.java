package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.entity.User;

import java.util.Collection;

public class GetUserConverter implements Converter<GetUserDTO, User> {

    @Override
    public GetUserDTO convertOne(User obj) {
        System.out.println("convertendo user");
        return obj != null ? new GetUserDTO(obj) : null;
    }

    @Override
    public Collection<GetUserDTO> convertAll(Collection<User> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
