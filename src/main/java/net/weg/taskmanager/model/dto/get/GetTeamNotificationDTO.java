package net.weg.taskmanager.model.dto.get;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.TeamNotification;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetTeamNotificationDTO {
    private Long id;
    private Collection<ShortUserDTO> notificatedUsers;
    private ShortUserDTO actionUser;
    private boolean readed;
    private String value;
    private LocalDateTime dateTime;
    private String type;

    public GetTeamNotificationDTO(TeamNotification teamNotification) {
        BeanUtils.copyProperties(teamNotification,this);
        this.notificatedUsers
                = teamNotification.getNotificatedUsers() != null ?
                teamNotification.getNotificatedUsers().stream().map(ShortUserDTO::new).toList() : new ArrayList<>();
        this.actionUser = teamNotification.getActionUser() != null ? new ShortUserDTO(teamNotification.getActionUser()) : null;
    }
}
