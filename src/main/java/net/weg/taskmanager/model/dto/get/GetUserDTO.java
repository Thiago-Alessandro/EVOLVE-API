package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.UserTeamDTO;
import net.weg.taskmanager.model.dto.UserTeamDTO2;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.*;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTaskConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.entity.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

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
//    private Collection<GetTeamDTO> managedTeams;
    //troca5r por dtos
    private Collection<UserTeamDTO2> teamRoles;
    private String theme;
    private String primaryColor;
    private String secondaryColor;
    private String primaryDarkColor;
    private String secondaryDarkColor;
    private Integer fontSize;
    public GetUserDTO(User user){
        Converter<GetFileDTO, File> fileConverter = new GetFileConverter();
        Converter<ShortTaskDTO, Task> taskConverter = new ShortTaskConverter();
        Converter<GetUserChatDTO, UserChat> userChatConverter = new GetUserChatConverter();
//        Converter<GetTeamDTO, Team> teamConverter = new GetTeamConverter();

        BeanUtils.copyProperties(user, this);
        this.image = fileConverter.convertOne(user.getImage());
        this.createdTasks = taskConverter.convertAll(user.getCreatedTasks());
        this.chats = userChatConverter.convertAll(user.getChats());
//        this.teams = teamConverter.convertAll(user.getTeams());
        this.teamRoles = user.getTeamRoles() != null ? user.getTeamRoles().stream().map(UserTeamDTO2::new).toList() : new ArrayList<>();
//        this.managedTeams = teamConverter.convertAll(user.getManagedTeams());
    }

}
