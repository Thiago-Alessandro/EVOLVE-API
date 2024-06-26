package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.UserProjectDTO;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTeamConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortProjectDTO {

    private Long id;
    private String name;
//    private String description;

    private Boolean favorited;
    private GetFileDTO image;
    private String imageColor;
//    private ShortUserDTO creator;
    private LocalDate finalDate;
//    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;

//    private Collection<GetUserDTO> administrators;
//    private Collection<Property> properties;
    private Collection<Status> statusList;
//    private Collection<UserProjectDTO> members;
    private ShortTeamDTO team;

//    private ShortProjectChatDTO chat;
//    private Collection<GetTaskDTO> tasks;

    public ShortProjectDTO(Project project){
        GetFileConverter fileConverter = new GetFileConverter();
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        Converter<ShortTeamDTO, Team> teamConverter = new ShortTeamConverter();
        BeanUtils.copyProperties(project, this);
        this.image = fileConverter.convertOne(project.getImage(), false);
//        this.members = userConverter.convertAll(project.getMembers());
//        this.members = project.getMembers() !=null ? project.getMembers().stream().map(UserProjectDTO::new).toList() : new ArrayList<>();
        this.team = teamConverter.convertOne(project.getTeam());
//        this.creator = userConverter.convertOne(project.getCreator());
    }

}
