package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserChat;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {

    private Long id;
    private String name;
//    private String password;
    private String email;
    private GetFileDTO image;
    private String imageColor;
    private Collection<GetUserChatDTO> chats;
    private Collection<ShortTaskDTO> createdTasks;
    private Collection<GetTeamDTO> managedTeams;
    private Collection<GetTeamDTO> teams;

    public GetUserDTO(User user){
        BeanUtils.copyProperties(user, this);
        this.image = DTOUtils.fileToGetFileDTO(user.getImage());
        this.createdTasks = DTOUtils.tasksToShortGetTaskDTOS(user.getCreatedTasks());
        this.chats = DTOUtils.chatToGetUserChatDTOS(user.getChats());
        this.teams = DTOUtils.teamsToGetTeamDTOS(user.getTeams());
        this.managedTeams = DTOUtils.teamsToGetTeamDTOS(user.getManagedTeams());
    }

}
