package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.weg.taskmanager.model.dto.shortDTOs.UserShortDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamChat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamDTO{

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
    private UserShortDTO administrator;
    private Collection<UserShortDTO> participants;
    private Collection<GetProjectDTO> projects;
    private GetTeamChatDTO chat;
    private Boolean personalWorkspace;

    public GetTeamDTO(Team team){
        BeanUtils.copyProperties(team, this);
        this.participants = DTOUtils.usersToUserShort(team.getParticipants());
        this.administrator = DTOUtils.userToUserShort(team.getAdministrator());
        this.image = DTOUtils.fileToGetFileDTO(team.getImage());
        this.chat = DTOUtils.chatToGetTeamChatDTO(team.getChat());
        this.projects = DTOUtils.projectToGetProjectDTOS(team.getProjects());
    }

}
