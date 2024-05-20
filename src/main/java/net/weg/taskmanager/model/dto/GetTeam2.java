package net.weg.taskmanager.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import net.weg.taskmanager.model.dto.UserTeamDTO;
import net.weg.taskmanager.model.dto.converter.shorts.ShortProjectConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTeamChatDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
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
public class GetTeam2{

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
    //    private ShortUserDTO administrator;
    //cpa transformar em DTO hein
    private Collection<ShortUserTeamDTO> participants;
    private Collection<ShortProjectDTO> projects;
    private GetTeamChatDTO chat;
    private Boolean personalWorkspace;
    private String code;

    public GetTeam2(Team team){
//        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        GetFileConverter fileDTOConverter = new GetFileConverter();
        Converter<GetTeamChatDTO, TeamChat> teamChatDTOConverter = new GetTeamChatConverter();
        Converter<ShortProjectDTO, Project> projectDTOConverter = new ShortProjectConverter();

        BeanUtils.copyProperties(team, this);
//        this.participants = team.getParticipants() != null ? team.getParticipants().stream().map(UserTeamDTO::new).toList() : new ArrayList<>();
        this.participants = team.getParticipants() != null ? team.getParticipants().stream().map(ShortUserTeamDTO::new).toList() : new ArrayList<>();
//        this.administrator = shortUserConverter.convertOne(team.getAdministrator());
        this.image = fileDTOConverter.convertOne(team.getImage(), false);
        this.chat = teamChatDTOConverter.convertOne(team.getChat());
        this.projects = projectDTOConverter.convertAll(team.getProjects());
    }

}
