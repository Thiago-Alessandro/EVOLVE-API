package net.weg.taskmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortProjectConverter;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
public class UserProjectDTO {

    private Long userId;
    private Long projectId;

    private GetUserDTO user;
    private ShortProjectDTO project;

    private Role role;

    private boolean manager = false;

    public UserProjectDTO(UserProject userProject){
        BeanUtils.copyProperties(userProject, this);
        Converter<GetUserDTO, User> userConverter = new GetUserConverter();
        Converter<ShortProjectDTO, Project> projectConverter = new ShortProjectConverter();
        this.user = userConverter.convertOne(userProject.getUser());
        this.project = projectConverter.convertOne(userProject.getProject());
    }

}
