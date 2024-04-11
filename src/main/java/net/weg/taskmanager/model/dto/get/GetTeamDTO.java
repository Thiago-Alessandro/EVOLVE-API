package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamChatConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.*;
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
    private ShortUserDTO administrator;
    private Collection<ShortUserDTO> participants;
    private Collection<GetProjectDTO> projects;
    private GetTeamChatDTO chat;
    private Boolean personalWorkspace;

    public GetTeamDTO(Team team){
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        Converter<GetFileDTO, File> fileDTOConverter = new GetFileConverter();
        Converter<GetTeamChatDTO, TeamChat> teamChatDTOConverter = new GetTeamChatConverter();
        Converter<GetProjectDTO, Project> projectDTOConverter = new GetProjectConverter();

        BeanUtils.copyProperties(team, this);
        this.participants = shortUserConverter.convertAll(team.getParticipants());
        this.administrator = shortUserConverter.convertOne(team.getAdministrator());
        this.image = fileDTOConverter.convertOne(team.getImage());
        this.chat = teamChatDTOConverter.convertOne(team.getChat());
        this.projects = projectDTOConverter.convertAll(team.getProjects());
    }

}
