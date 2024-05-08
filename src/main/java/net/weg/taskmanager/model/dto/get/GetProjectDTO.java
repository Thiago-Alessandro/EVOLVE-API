package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectChatConverter;
import net.weg.taskmanager.model.dto.converter.get.GetTaskConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTaskConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTeamConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.property.Property;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectDTO {

    private Long id;
    private String name;
    private String description;

    private Boolean favorited;
    private GetFileDTO image;
    private String imageColor;
    private ShortUserDTO creator;
    private LocalDate finalDate;
    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;

    //vai continuar msm?
    private Collection<ShortUserDTO> administrators;
    private Collection<Property> properties;
    private Collection<Status> statusList;
    private Collection<ShortUserDTO> members;
    private Collection<Chart> charts;
    private Collection<GetCommentDTO> comments;
    private ShortTeamDTO team;

    @JsonIgnore
    private GetProjectChatDTO chat;
    private Collection<GetTaskDTO> tasks;

    public GetProjectDTO(Project project){
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        Converter<GetFileDTO, File> fileDTOConverter = new GetFileConverter();
        Converter<GetProjectChatDTO, ProjectChat> projectChatDTOConverter = new GetProjectChatConverter();
        Converter<GetTaskDTO, Task> taskDTOCOnverter = new GetTaskConverter();
        Converter<ShortTeamDTO, Team> teamDTOTeamConverter = new ShortTeamConverter();

        BeanUtils.copyProperties(project, this);
        this.image = fileDTOConverter.convertOne(project.getImage());
        this.team = teamDTOTeamConverter.convertOne(project.getTeam());
        this.chat = projectChatDTOConverter.convertOne(project.getChat());
        this.administrators = shortUserConverter.convertAll(project.getAdministrators());
        this.members = shortUserConverter.convertAll(project.getMembers());
        this.creator = shortUserConverter.convertOne(project.getCreator());
        this.tasks = taskDTOCOnverter.convertAll(project.getTasks());
        this.comments = project.getComments() != null ? project.getComments().stream().map(GetCommentDTO::new).toList() : null;

    }

}
