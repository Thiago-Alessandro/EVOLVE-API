package net.weg.taskmanager.model.dto.get;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import net.weg.taskmanager.model.dto.UserTeamDTO;
import org.springframework.beans.BeanUtils;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamChatConverter;
import net.weg.taskmanager.model.entity.*;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamDTO{

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
//    private ShortUserDTO administrator;
    //cpa transformar em DTO hein
    private Collection<UserTeamDTO> participants;
    private Collection<GetProjectDTO> projects;
    private GetTeamChatDTO chat;
    private Boolean personalWorkspace;
    private Collection<GetTeamNotificationDTO> notifications;
    private String code;


    public GetTeamDTO(Team team){
//        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        GetFileConverter fileDTOConverter = new GetFileConverter();
        Converter<GetTeamChatDTO, TeamChat> teamChatDTOConverter = new GetTeamChatConverter();
        Converter<GetProjectDTO, Project> projectDTOConverter = new GetProjectConverter();

        BeanUtils.copyProperties(team, this);
        this.participants = team.getParticipants() != null ? team.getParticipants().stream().map(UserTeamDTO::new).toList() : new ArrayList<>();
//        this.administrator = shortUserConverter.convertOne(team.getAdministrator());
        this.image = fileDTOConverter.convertOne(team.getImage(), false);
        this.chat = teamChatDTOConverter.convertOne(team.getChat());
        this.projects = projectDTOConverter.convertAll(team.getProjects());
        this.notifications = team.getNotifications() != null ? team.getNotifications().stream().map(GetTeamNotificationDTO::new).toList() : null;
    }

}
