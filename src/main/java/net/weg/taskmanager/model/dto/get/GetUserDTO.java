package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.*;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTaskConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.entity.*;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {

    private Long id;
    private String name;
    private String email;
    private GetFileDTO image;
    private String imageColor;
    private Collection<GetUserChatDTO> chats;
    private Collection<ShortTaskDTO> createdTasks;
    private Collection<GetTeamDTO> managedTeams;
    private Collection<GetTeamDTO> teams;
    private String theme;
    private String primaryColor;
    private String secondaryColor;
    private String primaryDarkColor;
    private String secondaryDarkColor;
    public GetUserDTO(User user){
        Converter<GetFileDTO, File> fileConverter = new GetFileConverter();
        Converter<ShortTaskDTO, Task> taskConverter = new ShortTaskConverter();
        Converter<GetUserChatDTO, UserChat> userChatConverter = new GetUserChatConverter();
        Converter<GetTeamDTO, Team> teamConverter = new GetTeamConverter();

        BeanUtils.copyProperties(user, this);
        this.image = fileConverter.convertOne(user.getImage());
        this.createdTasks = taskConverter.convertAll(user.getCreatedTasks());
        this.chats = userChatConverter.convertAll(user.getChats());
        this.teams = teamConverter.convertAll(user.getTeams());
        this.managedTeams = teamConverter.convertAll(user.getManagedTeams());
    }

}
