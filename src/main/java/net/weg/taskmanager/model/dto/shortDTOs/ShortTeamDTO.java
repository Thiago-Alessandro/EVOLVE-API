package net.weg.taskmanager.model.dto.shortDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTeam;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTeamDTO {

    private Long id;
    private String name;
    private GetFileDTO image;
    private String imageColor;
//    private ShortUserDTO administrator;
    @JsonIgnore
    //tirar e botar dtos dps
    private Collection<UserTeam> participants;

    public ShortTeamDTO(Team team){
        Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
        Converter<GetFileDTO, File> fileConverter = new GetFileConverter();
        BeanUtils.copyProperties(team, this);

//        this.administrator = userConverter.convertOne(team.getAdministrator());
//        this.participants = userConverter.convertAll(team.getParticipants();
        this.participants = team.getParticipants();
        this.image = fileConverter.convertOne(team.getImage());

    }

}
