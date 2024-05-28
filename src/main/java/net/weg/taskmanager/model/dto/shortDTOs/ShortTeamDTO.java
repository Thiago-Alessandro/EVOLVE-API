package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.ShortUserTeamDTO;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.get.GetTeamNotificationDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTeamDTO {

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
    private Collection<ShortUserTeamDTO> participants;
    private Collection<GetTeamNotificationDTO> notifications;
    private String code;

    public ShortTeamDTO(Team team){
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        GetFileConverter fileConverter = new GetFileConverter();
        BeanUtils.copyProperties(team, this);

//        this.administrator = userConverter.convertOne(team.getAdministrator());
//        this.participants = userConverter.convertAll(team.getParticipants();
        this.participants = team.getParticipants() != null? team.getParticipants().stream().map(ShortUserTeamDTO::new).toList() : new ArrayList<>();
        this.image = fileConverter.convertOne(team.getImage(), false);
        this.notifications = team.getNotifications() != null ?
                team.getNotifications().stream().map(GetTeamNotificationDTO::new).toList()
                : new ArrayList<>();
    }

    public ShortTeamDTO(Long id){
        this.id = id;
    }

}
