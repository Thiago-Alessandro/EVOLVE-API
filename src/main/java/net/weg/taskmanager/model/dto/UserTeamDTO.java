package net.weg.taskmanager.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTeamConverter;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
public class UserTeamDTO {

    private Long userId;
    private Long teamId;

    private GetUserDTO user;
    private ShortTeamDTO team;

    private Role role;
    private boolean isManager;

    public UserTeamDTO(UserTeam userTeam) {
        BeanUtils.copyProperties(userTeam, this);
        Converter<GetUserDTO, User> userConverter = new GetUserConverter();
        Converter<ShortTeamDTO, Team> teamConverter = new ShortTeamConverter();

        this.user = userConverter.convertOne(userTeam.getUser());
        this.team = teamConverter.convertOne(userTeam.getTeam());
    }

}
