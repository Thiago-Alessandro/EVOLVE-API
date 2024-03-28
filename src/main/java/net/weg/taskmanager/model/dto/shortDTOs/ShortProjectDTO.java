package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ShortUserDTO creator;
    private LocalDate finalDate;
//    private LocalDate creationDate;
    private LocalDateTime lastTimeEdited;

//    private Collection<GetUserDTO> administrators;
//    private Collection<Property> properties;
    private Collection<Status> statusList;
    private Collection<ShortUserDTO> members;
    private ShortTeamDTO team;

//    private ShortProjectChatDTO chat;
//    private Collection<GetTaskDTO> tasks;

    public ShortProjectDTO(Project project){
        BeanUtils.copyProperties(project, this);
        this.image = DTOUtils.fileToGetFileDTO(project.getImage());
        this.members = DTOUtils.usersToShortUserDTO(project.getMembers());
        this.team = DTOUtils.teamToShortTeamDTO(project.getTeam());
        this.creator = DTOUtils.userToShortUserDTO(project.getCreator());
    }

}
