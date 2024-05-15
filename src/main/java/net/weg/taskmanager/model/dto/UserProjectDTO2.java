package net.weg.taskmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
public class UserProjectDTO2 {

    private Long userId;
    private Long projectId;

    private ShortUserDTO user;
    private GetProjectDTO project;

    private Role role;

    private boolean isManager = false;

    public UserProjectDTO2(UserProject userProject){
        BeanUtils.copyProperties(userProject, this);
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        Converter<GetProjectDTO, Project> projectConverter = new GetProjectConverter();
        this.user = userConverter.convertOne(userProject.getUser());
        this.project = projectConverter.convertOne(userProject.getProject());
    }

}
