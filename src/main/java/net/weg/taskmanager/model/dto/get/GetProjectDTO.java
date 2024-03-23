package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.shortDTOs.TeamShortDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.*;
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
    private GetUserDTO creator;
    private LocalDate finalDate;
    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;

    //vai continuar msm?
    private Collection<GetUserDTO> administrators;
    private Collection<Property> properties;
    private Collection<Status> statusList;
    private Collection<GetUserDTO> members;
    private TeamShortDTO team;

    @JsonIgnore
    private GetProjectChatDTO chat;
    private Collection<GetTaskDTO> tasks;

    public GetProjectDTO(Project project){
        BeanUtils.copyProperties(project, this);
        this.image = DTOUtils.fileToGetFileDTO(project.getImage());
        this.chat = DTOUtils.chatToGetProjectChatDTO(project.getChat());
        this.administrators = DTOUtils.usersToGetUserDTOs(project.getAdministrators());
        this.members = DTOUtils.usersToGetUserDTOs(project.getMembers());
        this.tasks = DTOUtils.tasksToGetTaskDTOS(project.getTasks());
    }

}
